package ru.ibs.project.dto.hhDTO.respAllVacanciesDTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVacanciesHHDTO {  //DTO from hh.ru GET /vacancies
    private Long found;
    private Long pages;
    private Long per_page;
    private Long page;
    private List<ItemDTO> items;
//    List<ErrorDTO> errors;
}
