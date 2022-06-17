package com.workfit.service;


import com.workfit.domain.Plan;
import com.workfit.dto.plan.ReadPlanByDateDto;
import com.workfit.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    @Transactional
    public void savePlan(Plan plan){ // 저장과 수정 둘다 하는 메서드임
        planRepository.save(plan);
    }

    public Plan findOne(Long planId){
        return planRepository.findOne(planId);
    }

    public List<Plan> findAllPlans(int offst, int limit){
        return planRepository.findAllWithMember(offst, limit);
    }

    public List<Plan> findAllByUserId(Long id, int offset, int limit) {
        return planRepository.findAllByUserId(id, offset, limit);
    }

    public List<Plan> findAllByUserIdDate(Long id, int offset, int limit, ReadPlanByDateDto readPlanByDateDto) {
        return planRepository.findAllByUserIdDate(id, offset, limit, readPlanByDateDto);
    }
    @Transactional
    public void updatePlan(Long id, int reps){
        Plan plan = planRepository.findOne(id);
        plan.update(reps);
    }


}
