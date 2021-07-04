package ru.ibs.project.resumeApp.dto.hhDTO.respAllResumesDTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.AreaDTO;
import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.EmployerDTO;
import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.SalaryDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTOResume {
    private String id;  // for request https://api.hh.ru/vacancies/44856835 for ??
//    private String title;  //желаемая должность
//    private String first_name;
//    private String last_name;
//    private String middle_name;
//    private Long age;
//    private AreaDTO area;

    // регион, контактные данные,
    // опыт работы. уметь парсить релевантный опыт
    //желаемая зп (от и до??)


//    private String name; //vacancy name
////    private AreaDTO area; //here is city name
//    private SalaryDTO salary; //here is from, to, currency
//    private EmployerDTO employer;  //here is employer name
}
