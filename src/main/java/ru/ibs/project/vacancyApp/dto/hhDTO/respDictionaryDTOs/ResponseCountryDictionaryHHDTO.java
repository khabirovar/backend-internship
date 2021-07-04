package ru.ibs.project.vacancyApp.dto.hhDTO.respDictionaryDTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCountryDictionaryHHDTO { //for list country id
    private Long id; //for list country Id
    private String name;
}
