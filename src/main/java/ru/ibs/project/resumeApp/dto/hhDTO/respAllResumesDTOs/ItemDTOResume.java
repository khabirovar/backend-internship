package ru.ibs.project.resumeApp.dto.hhDTO.respAllResumesDTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTOResume {
    private String id;  // for request https://api.hh.ru/resumes/44856835
}
