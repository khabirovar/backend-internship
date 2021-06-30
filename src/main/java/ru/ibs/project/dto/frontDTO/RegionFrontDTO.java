package ru.ibs.project.dto.frontDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionFrontDTO {  //DTO for front
    private String region;
    private List<VacancyFrontDTO> vacancyFrontDTOs;
}
