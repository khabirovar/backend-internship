package ru.ibs.project.vacancyApp.dto.hhDTO.respDictionaryDTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCountryHHDTO { //for GET https://api.hh.ru/areas/113
    private String name; //Country name: Россия
    private List<RegionDTO> areas; //Нижегородская обл. и т.д.
}
