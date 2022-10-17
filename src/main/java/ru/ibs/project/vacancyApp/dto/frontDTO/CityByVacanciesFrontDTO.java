package ru.ibs.project.vacancyApp.dto.frontDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityByVacanciesFrontDTO {
    private String nameCity;
    private String nameRegion;
    private Long countVacancies;
    private Long minSalary;
    private Long maxSalary;
    private Long medianValue;
}
