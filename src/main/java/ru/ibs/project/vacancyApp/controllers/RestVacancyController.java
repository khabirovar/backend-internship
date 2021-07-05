package ru.ibs.project.vacancyApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ibs.project.entities.VacancyArea;
import ru.ibs.project.services.interfaces.VacancyAreaService;

import java.util.List;

@RestController
@RequestMapping(value = "/vacancies", consumes = {MediaType.ALL_VALUE},
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class RestVacancyController {

    private VacancyAreaService vacancyAreaService;

    @Autowired
    public RestVacancyController(VacancyAreaService vacancyAreaService) {
        this.vacancyAreaService = vacancyAreaService;
    }

    @GetMapping("all-vacancies")     //just array all vacancies
    public List<VacancyArea> readAllVacancies() {
        return vacancyAreaService.createAllVacancyAreaList();
    }
}
