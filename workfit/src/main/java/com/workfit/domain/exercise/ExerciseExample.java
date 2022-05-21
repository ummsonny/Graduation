package com.workfit.domain.exercise;

import com.workfit.domain.Daily;
import com.workfit.domain.Plan;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Data
public abstract class ExerciseExample {

    @Id
    @GeneratedValue
    @Column(name = "exercise_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id") // FK로 선언한 것이다 // 여기가 변경되면 member_id FK 값이 다른 멤버로 변경된다
    private Plan plan;

    @OneToOne(mappedBy = "daily", fetch = LAZY)
    private Daily daily;

}
