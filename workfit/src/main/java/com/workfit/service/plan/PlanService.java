package com.workfit.service.plan;

import com.workfit.controller.dto.PlanDto;
import com.workfit.domain.Daily;
import com.workfit.domain.Exercise;
import com.workfit.domain.Member;
import com.workfit.domain.Plan;
import com.workfit.repository.DailyRepository;
import com.workfit.repository.ExerciseRepository;
import com.workfit.repository.MemberRepository;
import com.workfit.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanService {

    private final MemberRepository memberRepository;
    private final ExerciseRepository exerciseRepository;
    private final DailyRepository dailyRepository;
    private final PlanRepository planRepository;
    private final EntityManager em;

    @Transactional
    public void savePlan(PlanDto planDto){
        Member member = memberRepository.getById(planDto.getMemberId());

        Plan plan = Plan.builder()
                .weight(planDto.getWeight())
                .member(member)
                .build();

        for (int i = 0 ; i < planDto.getExerciseTypes().length ; i++){
            Exercise exercise = exerciseRepository.findByExerciseName(planDto.getExerciseTypes()[i]);

            Daily daily = Daily.builder()
                    .exercise(exercise)
                    .reps(planDto.getReps()[i])
                    .build();

            plan.addDailies(daily);

            dailyRepository.save(daily);

        }

        planRepository.save(plan);
    }

    public List<Plan> findPlans(){
        return planRepository.findAll();
    }

    /**
     * 2022.05.18 여기서 끝남
     */
    public void findWeight(){
         PlanAggrDto planWeightDto = (PlanAggrDto) em.createQuery("select min(weight) as 'minWeight', max(weight) as 'maxWeight'" +
                "from plan " +
                "group by member_id " +
                "having member_id = `id`" +
                "");
         // setParameter 필요;

         String curWeight = String.valueOf(em.createQuery("select weight " +
                 "from plan " +
                 "where member_id = `id` " +
                 "order by date desc")
                 .setMaxResults(1));





    }



}
