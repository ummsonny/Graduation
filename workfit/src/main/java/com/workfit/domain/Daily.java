package com.workfit.domain;

import com.workfit.domain.exercise.ExerciseExample;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Daily {

    @Id
    @GeneratedValue
    @Column(name = "daily_id")
    private  Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id") // FK로 선언한 것이다 // 여기가 변경되면 member_id FK 값이 다른 멤버로 변경된다
    private Plan plan;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private Long reps;

    @Builder
    public Daily(Exercise exercise, Long reps) {
        this.exercise = exercise;
        this.reps = reps;
    }
}
