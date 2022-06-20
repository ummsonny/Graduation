package com.workfit.api;

import com.workfit.domain.*;
import com.workfit.dto.plan.*;
import com.workfit.service.ExerciseService;
import com.workfit.service.MemberService;
import com.workfit.service.PlanService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
//    @GetMapping("plan")
//    public List<PlanDto> getAllPlan(@RequestParam(value = "offset", defaultValue = "0") int offset,
//                                    @RequestParam(value = "limit", defaultValue = "100") int limit){
//        List<Plan> plans = planService.findAllPlans(offset, limit); //쿼리
//        List<PlanDto> result = plans.stream()
//                .map(p -> new PlanDto(p))
//                .collect(Collectors.toList());
//        return result;
//    }

    @GetMapping("plan/user")
    public Result<List<PlanDto>> getPlanById(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                             @RequestParam(value = "limit", defaultValue = "100") int limit){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        // 1. 몸무게
        List<Plan> plans = planService.findAllByUserPrincipal(email, offset, limit);
        List<PlanDto> result = plans.stream()
                .map(p -> new PlanDto(p))
                .collect(Collectors.toList());
        return new Result<List<PlanDto>>(result.size(), result);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }

//    @GetMapping("plan/user/date/{id}")
//    public List<PlanDto> getPlanByIdDate(@PathVariable Long id,
//                                         @RequestParam(value = "offset", defaultValue = "0") int offset,
//                                         @RequestParam(value = "limit", defaultValue = "100") int limit,
//                                         @RequestBody ReadPlanByDateDto readPlanByDateDto){
//        List<Plan> plans = planService.findAllByUserIdDate(id, offset, limit, readPlanByDateDto);
//        List<PlanDto> result = plans.stream()
//                .map(p -> new PlanDto(p))
//                .collect(Collectors.toList());
//        return result;
//    }

    /**
     * 저장
     * 1. 특정 회원의 계획 단건 저장
     */
    @PostMapping("plan/user")
    public ResponseEntity<CreatePlanResponse> savePlan(@RequestBody @Valid CreatePlanExerciseRequest createPlanExerciseRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        Member member = memberService.findByEmail(email);
        BodyInfo bodyInfo = new BodyInfo(90L,180L);
        member.setBodyInfo(bodyInfo);


        Optional<Plan> cur_plan = planService.findByPlanDate(LocalDate.now());
        Plan n_plan = cur_plan.orElse(null);

        if(n_plan == null) {//새롭게 만들기
            List<PlanExercise> planExercises = new ArrayList<>();
            Exercise exercise = exerciseService.findByName(createPlanExerciseRequest.getExerciseName());
            PlanExercise planExercise = PlanExercise.createPlanExercise(exercise, createPlanExerciseRequest.getReps());
            planExercises.add(planExercise);
            n_plan = Plan.createOrder(member, planExercises, LocalDate.now());
        }else{ // 기존 있는거
            Exercise exercise = exerciseService.findByName(createPlanExerciseRequest.getExerciseName());
            PlanExercise planExercise = PlanExercise.createPlanExercise(exercise, createPlanExerciseRequest.getReps());
            n_plan.addPlanExercise(planExercise);
        }
        planService.savePlan(n_plan);

        return ResponseEntity.status(HttpStatus.OK).body(new CreatePlanResponse("오늘도 화이팅!"));

    }
    /**
     * 저장
     * 2. 특정 회원의 계획 다건 저장
     */
//    @PostMapping("plan/user/dd")
//    public ResponseEntity<CreatePlanResponse> savePlans(@RequestBody @Valid CreatePlanRequest createPlanRequest){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = (String) authentication.getPrincipal();
//        Member member = memberService.findByEmail(email);
//        System.out.println("Call the POST");
//        for(CreatePlanExerciseRequest cc : createPlanRequest.getPlanExercises()){
//            System.out.println(cc.getWeight()+" "+cc.getExerciseName()+" "+cc.getReps());
//        }
//        List<PlanExercise> planExercises = new ArrayList<>();
//        for(CreatePlanExerciseRequest createPlanExerciseRequest : createPlanRequest.getPlanExercises()){
//            Exercise exercise = exerciseService.findByName(createPlanExerciseRequest.getExerciseName());
//            PlanExercise planExercise = PlanExercise.createPlanExercise(exercise, createPlanExerciseRequest.getReps());
//            planExercises.add(planExercise);
//        }
//        Plan plan = Plan.createOrder(member, planExercises, LocalDate.now());
//        planService.savePlan(plan);
//
//        return ResponseEntity.status(HttpStatus.OK).body(new CreatePlanResponse("오늘도 화이팅!"));
//
//    }

}
