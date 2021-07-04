package ru.ibs.project.vacancyApp.dto.hhDTO.respDictionaryDTOs;

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
public class RegionDTO {
    private String name; //Нижегородская обл. и т.д.
    private List<AreaDTO> areas; //here is city name
}
