package ru.ibs.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ibs.project.entities.VacancyArea;
import ru.ibs.project.services.interfaces.VacancyAreaService;

import java.util.List;

@RestController
@RequestMapping(value = "/vacancies", consumes = {MediaType.ALL_VALUE},
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RestVacancyController {

    @Autowired
    private VacancyAreaService vacancyAreaService;

    @GetMapping("all-vacancies")     //+массив всех вакансий
    public List<VacancyArea> readAllVacancies(){
        return vacancyAreaService.createAllVacancyAreaList();
    }

}
