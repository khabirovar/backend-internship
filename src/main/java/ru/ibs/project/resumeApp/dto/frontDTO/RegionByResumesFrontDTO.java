package ru.ibs.project.resumeApp.dto.frontDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionByResumesFrontDTO {
    private String nameRegion;
    private Long countCities;
    private Long countResumes;
    private Long minSalary;
    private Long maxSalary;
    private Long medianValue;
}
