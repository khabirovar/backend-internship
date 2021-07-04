package ru.ibs.project.resumeApp.dto.hhDTO.respResumeDTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.AreaDTO;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDTO {
    private String id;
    private String title;  //желаемая должность
    private String first_name;
    private String last_name;
    private String middle_name;
    private Long age;
    private AreaDTO area;  //nameCity
    private List<ContactDTO> contact;
    private SalaryResumeDTO salary;
    private List<ExperienceDTOResume> experience;
    private TotalExperienceDTO total_experience;
}
