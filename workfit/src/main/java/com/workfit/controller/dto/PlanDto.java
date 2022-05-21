package com.workfit.controller.dto;

import lombok.Data;

@Data
public class PlanDto {

    private Long memberId;
    private Long weight;
    private String ExerciseTypes[];
    private Long reps[];

}
