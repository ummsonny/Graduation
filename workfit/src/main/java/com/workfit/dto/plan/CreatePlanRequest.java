package com.workfit.dto.plan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlanRequest {
    private List<CreatePlanExerciseRequest> planExercises = new ArrayList<>();
}
