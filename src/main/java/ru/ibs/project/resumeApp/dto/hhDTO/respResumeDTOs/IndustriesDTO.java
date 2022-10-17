package ru.ibs.project.resumeApp.dto.hhDTO.respResumeDTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndustriesDTO {
    private String id;
    private String name;  //например "Разработка программного обеспечения"
}
