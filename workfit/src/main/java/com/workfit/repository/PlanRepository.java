package com.workfit.repository;

import com.workfit.domain.Plan;
import com.workfit.dto.plan.ReadPlanByDateDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PlanRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Plan plan){ // 저장과 수정 둘다 하는 메서드임
        if (plan.getLocalDate() == null){
            em.persist(plan); //저장
        }else{
            em.merge(plan); //merge는 이미 있는 거 update
        }
    }

    public Plan findOne(Long id){
        return em.find(Plan.class, id);
    }

    public List<Plan> findAllWithMember(int offset, int limit){
        return em.createQuery(
                "select p from Plan p"+
                        " join fetch p.member m", Plan.class) //join앞에 띄어쓰기 해야함
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }


    public List<Plan> findAllByUserId(Long id, int offset, int limit) {
        return em.createQuery(
                "select p from Plan p"+
                        " join fetch p.member m"+
                        " where m.id =: id", Plan.class)
                .setParameter("id", id)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Plan> findAllByUserIdDate(Long id, int offset, int limit, ReadPlanByDateDto readPlanByDateDto) {
        return em.createQuery(
                        "select p from Plan p"+
                                " join fetch p.member m"+
                                " where m.id =: id"+
                        " and p.localDate between :startDate and :endDate", Plan.class)
                .setParameter("id", id)
                .setParameter("startDate", readPlanByDateDto.getStartDate())
                .setParameter("endDate", readPlanByDateDto.getEndDate())
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
