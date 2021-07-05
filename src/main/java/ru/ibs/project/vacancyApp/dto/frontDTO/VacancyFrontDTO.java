package ru.ibs.project.vacancyApp.dto.frontDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacancyFrontDTO {
    private String nameCity;
    private String nameVacancy;
    private Long fromSalary;
    private Long toSalary;
    private String currencySalary;
    private String nameEmployer;
    private String nameExperience;
}
