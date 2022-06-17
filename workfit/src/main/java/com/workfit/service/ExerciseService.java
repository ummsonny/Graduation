package com.workfit.service;

import com.workfit.domain.Exercise;
import com.workfit.domain.Plan;
import com.workfit.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;


    @Transactional
    public void saveExercise(Exercise exercise){ // 저장과 수정 둘다 하는 메서드임
        exerciseRepository.save(exercise);
    }

    public Exercise findOne(Long exerciseId){
        return exerciseRepository.findOne(exerciseId);
    }

    public Exercise findByName(String name){
        return exerciseRepository.findByName(name);
    }

    @Transactional
    public void remove(Long id){
        exerciseRepository.remove(id);
    }
}
