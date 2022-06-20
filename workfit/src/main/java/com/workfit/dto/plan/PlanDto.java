package com.workfit.dto.plan;


import com.workfit.domain.BodyInfo;
import com.workfit.domain.Member;
import com.workfit.domain.Plan;
import com.workfit.domain.PlanExercise;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PlanDto {

    //private Long id;
    //private Member member;
//    private Long max_weight;
//    private Long min_weight;
    //    private Double bmi;
    private LocalDate localDate;
    private BodyInfo bodyInfo;

    private List<PlanExerciseDto> planExercises;

    public PlanDto(Plan plan){
        //this.id = plan.getId();
        //this.member = plan.getMember();
//        this.max_weight = plan.getMax_weight();
//        this.min_weight = plan.getMin_weight();
//        this.bmi = plan.getBmi();
        this.localDate = plan.getLocalDate();
        this.bodyInfo = plan.getMember().getBodyInfo();

        this.planExercises = plan.getPlanExercises().stream() //쿼리
                .map(planExercise -> new PlanExerciseDto(planExercise))
                .collect(Collectors.toList());
    }
}
