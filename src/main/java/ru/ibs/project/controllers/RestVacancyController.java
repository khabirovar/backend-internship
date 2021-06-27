package ru.ibs.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ibs.project.dto.frontDTO.DownloadRequestDTO;
import ru.ibs.project.dto.frontDTO.VacancyFrontDTO;
import ru.ibs.project.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;
import ru.ibs.project.services.interfaces.DeleteService;
import ru.ibs.project.services.interfaces.RTService;
import ru.ibs.project.services.interfaces.VacancyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/vacancies", consumes = {MediaType.ALL_VALUE},
        produces = MediaType.APPLICATION_JSON_VALUE)

public class RestVacancyController {

    private RTService rtService;

    private VacancyService vacancyService;

    private DeleteService deleteService;

    @Autowired
    public RestVacancyController(RTService rtService, VacancyService vacancyService, DeleteService deleteService) {
        this.rtService = rtService;
        this.vacancyService = vacancyService;
        this.deleteService = deleteService;
    }

    @PostMapping("create")
    public DownloadRequestDTO create(@RequestBody DownloadRequestDTO requestDTO) {
        /*
   {
        "name": "повар",
        "onlyWithSalary": true,
        "orderBy": "publication_time"
    }
         */
        Set<ItemDTO> vacanciesDTO = rtService.readAllFromHH(requestDTO);
        vacancyService.createAll(vacanciesDTO);
        vacanciesDTO.clear();
        return requestDTO;
    }

    @GetMapping("delete")
    public void delete(){
        deleteService.deleteVacancyArea();
        deleteService.deleteAreaAndVacancy();
    }

    @GetMapping("readAll") //считать все из БД +
    public List<VacancyFrontDTO> readAllVacancies(){
        List<VacancyFrontDTO> list = new ArrayList<>();
        return list;
    }

//    @PostMapping("downloadAll") //выкачать csv файл


}
