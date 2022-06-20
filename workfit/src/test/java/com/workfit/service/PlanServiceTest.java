package com.workfit.service;


import com.workfit.domain.Exercise;
import com.workfit.domain.Member;
import com.workfit.domain.Plan;
import com.workfit.domain.PlanExercise;
import com.workfit.repository.ExerciseRepository;
import com.workfit.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PlanServiceTest {

    @Autowired
    PlanService planService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ExerciseRepository exerciseRepository;

    @Test
    public void 계획등록(){
        //Given
        //persist 즉 저장되어있는애를 가지고 해야함
        Member member = new Member();
        member.setUserName("강민우");
        memberRepository.save(member);

        //persist 즉 저장되어있는애를 가지고 해야함
        Exercise exercise1 = new Exercise();
        exercise1.setExerciseName("라잉레그레이즈");
        exerciseRepository.save(exercise1);

        PlanExercise planExercise = new PlanExercise();
        planExercise.setExercise(exercise1);
        planExercise.setReps(18);

        Plan plan = new Plan();

        //when
        plan.setMember(member);
        plan.addPlanExercise(planExercise);
        plan.setMax_weight(99L);
        planService.savePlan(plan);

        //then
        Plan findplan = planService.findOne(plan.getId());

        assertEquals("강민우", findplan.getMember().getUsername());
        assertEquals("라잉레그레이즈", findplan.getPlanExercises().get(0).getExercise().getExerciseName());
        assertEquals(18, findplan.getPlanExercises().get(0).getReps());

    }

    @Test
    public void 계획수정(){
        //Given
        //persist 즉 저장되어있는애를 가지고 해야함
        Member member = new Member();
        member.setUserName("강민우");
        memberRepository.save(member);

        //persist 즉 저장되어있는애를 가지고 해야함
        Exercise exercise1 = new Exercise();
        exercise1.setExerciseName("라잉레그레이즈");
        exerciseRepository.save(exercise1);

        PlanExercise planExercise = new PlanExercise();
        planExercise.setExercise(exercise1);
        planExercise.setReps(18);

        Plan plan = new Plan();

        //when
        plan.setMember(member);
        plan.addPlanExercise(planExercise);
        plan.setMax_weight(99L);
        planService.savePlan(plan);

        //then

        Plan findplan = planService.findOne(plan.getId());
        findplan.getPlanExercises().get(0).getExercise().setExerciseName("푸쉬업");
        findplan.update(12);

        assertEquals("강민우", findplan.getMember().getUsername());
        assertEquals("푸쉬업", findplan.getPlanExercises().get(0).getExercise().getExerciseName());
        assertEquals(12L, findplan.getPlanExercises().get(0).getReps());
    }
}
