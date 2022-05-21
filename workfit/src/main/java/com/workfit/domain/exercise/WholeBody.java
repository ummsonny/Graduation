package com.workfit.domain.exercise;

import lombok.Data;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("W")
@Data
public class WholeBody extends ExerciseExample {

    private String exerciseType;
}
