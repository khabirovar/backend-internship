package ru.ibs.project.resumeApp.services.interfaces;

import ru.ibs.project.resumeApp.dto.hhDTO.respResumeDTOs.ResumeDTO;

import java.util.Set;

public interface DataManipulationServiceResume {
    void createAll(Set<ResumeDTO> resumeDTOSet);
}
