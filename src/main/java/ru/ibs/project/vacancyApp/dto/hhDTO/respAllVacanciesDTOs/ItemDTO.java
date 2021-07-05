package ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Long id;  // for request https://api.hh.ru/vacancies/44856835 for experience
    private String name; //vacancy name
    private AreaDTO area; //here is city name
    private SalaryDTO salary; //here is from, to, currency
    private EmployerDTO employer;  //here is employer name
}
