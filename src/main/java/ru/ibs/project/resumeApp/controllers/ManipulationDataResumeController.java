package ru.ibs.project.resumeApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ibs.project.resumeApp.dto.frontDTO.DownloadRequestDTOResume;
import ru.ibs.project.resumeApp.dto.hhDTO.respResumeDTOs.ResumeDTO;
import ru.ibs.project.resumeApp.services.interfaces.DataManipulationServiceResume;
import ru.ibs.project.resumeApp.services.interfaces.DownloadFromHHServiceResume;

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
    private DataManipulationServiceResume dataManipulationServiceResume;

    @PostMapping("create")
    public DownloadRequestDTOResume create(@RequestBody DownloadRequestDTOResume requestDTOResume) {
        //по /resumes? скачать все id
        // по GET /resumes/{resume_id} скачать информацию об этом человеке
        List<String> listIdResume = downloadFromHHServiceResume.downloadAllIdDTOsByDownloadRequestDTO(requestDTOResume);
        Set<ResumeDTO> resumeDTOSet = downloadFromHHServiceResume.downloadAllResumeById(listIdResume);
        dataManipulationServiceResume.createAll(resumeDTOSet);


//        Set<ItemDTOResume> resumesDTO = downloadFromHHService.downloadAllItemDTOsByDownloadRequestDTO(requestDTO);
//        dataManipulationService.createAll(vacanciesDTO);
//        vacanciesDTO.clear();
        return requestDTOResume;
    }
}
