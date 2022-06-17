package com.workfit.dto.plan;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor //모든 필드값을 이용한 생성자
@NoArgsConstructor //기본 생성자
public class ReadPlanByDateDto {
    private LocalDate startDate;
    private LocalDate endDate;
}
