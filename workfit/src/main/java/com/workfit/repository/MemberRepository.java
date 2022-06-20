package com.workfit.repository;


import com.workfit.domain.Member;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member findById(Long id){
        return em.find(Member.class, id);
    }
    public Member findByEmail(String email){
        return em.createQuery("select m from Member m where m.email =: email",Member.class)
                .setParameter("email",email)
                .getSingleResult();
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByNames(String name){
        return em.createQuery("select m from Member m where m.userName =: name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Member findByName(String username) {
        return em.createQuery("select m from Member m where m.userName =: username", Member.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
