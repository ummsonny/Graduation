package com.workfit.dto.plan;


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

    private Long id;
    private Member member;
    private Long max_weight;
    private Long min_weight;
    private LocalDate localDate;
    private Double bmi;

    private List<PlanExerciseDto> planExercises;

    public PlanDto(Plan plan){
        this.id = plan.getId();
        this.member = plan.getMember();
        this.max_weight = plan.getMax_weight();
        this.min_weight = plan.getMin_weight();
        this.localDate = plan.getLocalDate();
        this.bmi = plan.getBmi();

        this.planExercises = plan.getPlanExercises().stream() //쿼리
                .map(planExercise -> new PlanExerciseDto(planExercise))
                .collect(Collectors.toList());
    }
}
