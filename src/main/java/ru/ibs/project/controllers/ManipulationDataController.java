package ru.ibs.project.controllers;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ibs.project.dto.frontDTO.DownloadRequestDTO;
import ru.ibs.project.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;
import ru.ibs.project.entities.VacancyArea;
import ru.ibs.project.services.interfaces.DataManipulationService;
import ru.ibs.project.services.interfaces.DownloadFromHHService;
import ru.ibs.project.services.interfaces.VacancyAreaService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping(value = "/data-manipulation", consumes = {MediaType.ALL_VALUE},
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ManipulationDataController {

    @Autowired
    private DownloadFromHHService downloadFromHHService;

    @Autowired
    private DataManipulationService dataManipulationService;

    @Autowired
    private VacancyAreaService vacancyAreaService;


    @PostMapping("create")
    public DownloadRequestDTO create(@RequestBody DownloadRequestDTO requestDTO) {
        Set<ItemDTO> vacanciesDTO = downloadFromHHService.downloadAllItemDTOsByDownloadRequestDTO(requestDTO);
        dataManipulationService.createAll(vacanciesDTO);
        vacanciesDTO.clear();
        return requestDTO;
    }


    @GetMapping("delete")
    public void delete() {
        dataManipulationService.deleteVacancyArea();
        dataManipulationService.deleteAreaAndVacancy();
    }

    @GetMapping("export-vacancies-CSV")
    public void exportCSV(HttpServletResponse response) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        String filename = "vacancies.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");
        //create a csv writer
        StatefulBeanToCsv<VacancyArea> writer = new StatefulBeanToCsvBuilder<VacancyArea>(response
                .getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator('|')
                .withOrderedResults(false)
                .build();
        writer.write(vacancyAreaService.createAllVacancyAreaList());
    }
}