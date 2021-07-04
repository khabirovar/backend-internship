package ru.ibs.project.resumeApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ibs.project.resumeApp.dto.frontDTO.DownloadRequestDTOResume;
import ru.ibs.project.resumeApp.dto.hhDTO.respResumeDTOs.ResumeDTO;
import ru.ibs.project.services.interfaces.DataManipulationService;
import ru.ibs.project.services.interfaces.DownloadFromHHServiceResume;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "resume/data-manipulation", consumes = {MediaType.ALL_VALUE},
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class ManipulationDataResumeController {

    @Autowired
    private DownloadFromHHServiceResume downloadFromHHServiceResume;

    @Autowired
    private DataManipulationService dataManipulationService;

    @PostMapping("create")
    public DownloadRequestDTOResume create(@RequestBody DownloadRequestDTOResume requestDTOResume) {
        dataManipulationService.deleteAreaAndVacancy();
        dataManipulationService.deleteResume();
        List<String> listIdResume = downloadFromHHServiceResume.downloadAllIdDTOsByDownloadRequestDTO(requestDTOResume);
        Set<ResumeDTO> resumeDTOSet = downloadFromHHServiceResume.downloadAllResumeById(listIdResume);
        dataManipulationService.createAllResumes(resumeDTOSet);
        listIdResume.clear();
        resumeDTOSet.clear();
        return requestDTOResume;
    }
}
