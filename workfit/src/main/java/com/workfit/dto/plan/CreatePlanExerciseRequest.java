package com.workfit.dto.plan;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlanExerciseRequest {

    private Long weight;
    private String exerciseName;
    private int reps;
}
