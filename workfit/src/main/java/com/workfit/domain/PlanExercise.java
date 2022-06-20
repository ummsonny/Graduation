package com.workfit.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PlanExercise {

    @Id
    @GeneratedValue
    @Column(name = "plan_exercise_id")
    private Long id;

    private int reps;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    public void update(int reps) {
        setReps(reps);
    }

    //==생성 메서드==//
    public static PlanExercise createPlanExercise(Exercise exercise, int reps){
        PlanExercise planExercise = new PlanExercise();
        planExercise.setExercise(exercise);
        planExercise.setReps(reps);

        return planExercise;
    }
}
