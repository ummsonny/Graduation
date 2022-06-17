package com.workfit.dto.plan;


import com.workfit.domain.PlanExercise;
import lombok.Data;

@Data
public class PlanExerciseDto {

    private String exerciseName;
    private int reps;

    public PlanExerciseDto(PlanExercise planExercise){
        this.exerciseName = planExercise.getExercise().getExerciseName(); //쿼리
        this.reps = planExercise.getReps();
    }
}
