package ru.ibs.project.vacancyApp.dto.frontDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DownloadRequestDTO {
    private String name;
    private Boolean onlyWithSalary;
    private String orderBy;  //publication_time / salary_desc / salary_asc / relevance
}
