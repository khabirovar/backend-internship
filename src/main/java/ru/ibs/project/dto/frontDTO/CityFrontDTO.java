package ru.ibs.project.dto.frontDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityFrontDTO {
    private String nameCity;
    private String nameRegion;
    private Long countVacancies;
    private Long minSalary;
    private Long maxSalary;
    private Long medianValue;
}
