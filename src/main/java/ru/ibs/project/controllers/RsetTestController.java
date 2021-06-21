package ru.ibs.project.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ibs.project.entities.Vacancy;

@RestController
@RequestMapping(consumes = {MediaType.ALL_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
public class RsetTestController {

    @GetMapping("vacancies")
    public Vacancy readVacancies() {
        Vacancy vacancy = new Vacancy(1L, "Java Developer", "Москва");
        return vacancy;
    }

    @PostMapping("vacancies")
    public Vacancy create(@RequestBody Vacancy vacancy) {
        Long id = vacancy.getId();
        String name = vacancy.getName();
        String area = vacancy.getArea();
        final Vacancy updatedVacancy = new Vacancy(id, name, area);
        return updatedVacancy;
    }

    @GetMapping("vacanciesParam")
    public Vacancy create(@RequestParam Long id, String name, String area){
        final Vacancy updatedVacancy = new Vacancy(id, name, area);
        return updatedVacancy;
    }
}
