package com.workfit.service;


import com.workfit.domain.Exercise;
import com.workfit.repository.ExerciseRepository;
import org.hibernate.annotations.NaturalId;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ExerciseServiceTest {

    @Autowired
    ExerciseService exerciseService;
    @Autowired
    ExerciseRepository exerciseRepository;

    @Test
    public void 운동등록(){
        //given
        Exercise exercise = new Exercise();
        exercise.setExerciseName("풀업");

        //when
        exerciseService.saveExercise(exercise);

        //then
        Assertions.assertEquals(exercise, exerciseRepository.findOne(exercise.getId()));
    }

    @Test
    public void 운동삭제(){
        //given
        Exercise exercise = new Exercise();
        exercise.setExerciseName("풀업");
        exerciseService.saveExercise(exercise);

        //when
        exerciseService.remove(exercise.getId());

        //then
        Assertions.assertEquals(null, exerciseRepository.findOne(exercise.getId()));
    }
}
