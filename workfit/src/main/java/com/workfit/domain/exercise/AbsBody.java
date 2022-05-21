package com.workfit.domain.exercise;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Data
public class AbsBody extends ExerciseExample {

    private String exerciseType;

}
