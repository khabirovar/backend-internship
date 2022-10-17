package ru.ibs.project.resumeApp.controllers;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ibs.project.entities.Resume;
import ru.ibs.project.resumeApp.dto.frontDTO.DownloadRequestDTOResume;
import ru.ibs.project.resumeApp.dto.hhDTO.respResumeDTOs.ResumeDTO;
import ru.ibs.project.services.interfaces.DataManipulationService;
import ru.ibs.project.services.interfaces.DownloadFromHHServiceResume;
import ru.ibs.project.services.interfaces.ResumeService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "resume/data-manipulation", consumes = {MediaType.ALL_VALUE},
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class ManipulationDataResumeController {

    private DownloadFromHHServiceResume downloadFromHHServiceResume;

    private DataManipulationService dataManipulationService;

    private ResumeService resumeService;

    @Autowired
    public ManipulationDataResumeController(DataManipulationService dataManipulationService,
                                            DownloadFromHHServiceResume downloadFromHHServiceResume,
                                            ResumeService resumeService) {
        this.dataManipulationService = dataManipulationService;
        this.downloadFromHHServiceResume = downloadFromHHServiceResume;
        this.resumeService = resumeService;
    }

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

    @GetMapping("export-vacancies-CSV")
    public void exportCSV(HttpServletResponse response) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        String filename = "resumes.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");
        //create a csv writer
        StatefulBeanToCsv<Resume> writer = new StatefulBeanToCsvBuilder<Resume>(response
                .getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator('|')
                .withOrderedResults(false)
                .build();
        writer.write(resumeService.createAllResumeList());
    }
}
