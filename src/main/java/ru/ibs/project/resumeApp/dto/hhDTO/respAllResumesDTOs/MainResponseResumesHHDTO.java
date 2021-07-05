package ru.ibs.project.resumeApp.dto.hhDTO.respAllResumesDTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainResponseResumesHHDTO { //DTO from hh.ru GET /resumes
    private Long found;
    private Long pages;
    private Long per_page;
    private Long page;
    private List<ItemDTOResume> items;
}
