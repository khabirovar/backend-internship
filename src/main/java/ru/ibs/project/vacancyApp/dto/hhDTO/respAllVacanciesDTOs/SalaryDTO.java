package ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDTO {
    private Long from;
    private Long to;
    private String currency;
}
