package com.workfit.repository;

import com.workfit.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Exercise findByExerciseName(String exerciseName);
}

