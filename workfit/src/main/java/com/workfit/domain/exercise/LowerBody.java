package com.workfit.domain.exercise;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("L")
@Data
public class LowerBody extends ExerciseExample {

    private String exerciseType;
}
