package ru.ibs.project.services.interfaces;

import ru.ibs.project.resumeApp.dto.frontDTO.DownloadRequestDTOResume;
import ru.ibs.project.resumeApp.dto.hhDTO.respResumeDTOs.ResumeDTO;

import java.util.List;
import java.util.Set;

public interface DownloadFromHHServiceResume {
    List<String> downloadAllIdDTOsByDownloadRequestDTO(DownloadRequestDTOResume requestDTOResume);

    Set<ResumeDTO> downloadAllResumeById(List<String> listIdResume);

}
