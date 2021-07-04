package ru.ibs.project.resumeApp.dto.hhDTO.respResumeDTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private TypeDTO type;
//    private String value; //for email
    private ValueDTO value; //for phone

}
