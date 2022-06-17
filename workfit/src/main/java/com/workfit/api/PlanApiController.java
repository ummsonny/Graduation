package com.workfit.api;

import com.workfit.domain.Exercise;
import com.workfit.domain.Member;
import com.workfit.domain.Plan;
import com.workfit.domain.PlanExercise;
import com.workfit.dto.plan.*;
import com.workfit.service.ExerciseService;
import com.workfit.service.MemberService;
import com.workfit.service.PlanService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PlanApiController {
    private final PlanService planService;
    private final MemberService memberService;
    private final ExerciseService exerciseService;

    /**
     * 1. 조회
     */
    @GetMapping("plan")
    public List<PlanDto> getAllPlan(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                    @RequestParam(value = "limit", defaultValue = "100") int limit){
        List<Plan> plans = planService.findAllPlans(offset, limit); //쿼리
        List<PlanDto> result = plans.stream()
                .map(p -> new PlanDto(p))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("plan/user/{id}")
    public List<PlanDto> getPlanById(@PathVariable Long id,
                                     @RequestParam(value = "offset", defaultValue = "0") int offset,
                                     @RequestParam(value = "limit", defaultValue = "100") int limit){
        List<Plan> plans = planService.findAllByUserId(id, offset, limit);
        List<PlanDto> result = plans.stream()
                .map(p -> new PlanDto(p))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("plan/user/date/{id}")
    public List<PlanDto> getPlanByIdDate(@PathVariable Long id,
                                         @RequestParam(value = "offset", defaultValue = "0") int offset,
                                         @RequestParam(value = "limit", defaultValue = "100") int limit,
                                         @RequestBody ReadPlanByDateDto readPlanByDateDto){
        List<Plan> plans = planService.findAllByUserIdDate(id, offset, limit, readPlanByDateDto);
        List<PlanDto> result = plans.stream()
                .map(p -> new PlanDto(p))
                .collect(Collectors.toList());
        return result;
    }

    /**
     * 저장
     * 특정 회원의 계획 저장
     */
    @PostMapping(path = "plan/user/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatePlanResponse> savePlan(@PathVariable("id") Long id, @RequestBody @Valid CreatePlanRequest createPlanRequest){
        Member member = memberService.findOne(id);

        List<PlanExercise> planExercises = new ArrayList<>();
        for(CreatePlanExerciseRequest createPlanExerciseRequest : createPlanRequest.getPlanExercises()){
            Exercise exercise = exerciseService.findByName(createPlanExerciseRequest.getExerciseName());
            PlanExercise planExercise = PlanExercise.createPlanExercise(exercise, createPlanExerciseRequest.getReps());
            planExercises.add(planExercise);
        }
        Plan plan = Plan.createOrder(member, planExercises, LocalDate.now());
        planService.savePlan(plan);

        return ResponseEntity.status(HttpStatus.OK).body(new CreatePlanResponse("오늘도 화이팅!"));

    }
}
