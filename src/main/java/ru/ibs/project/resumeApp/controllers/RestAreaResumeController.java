package ru.ibs.project.resumeApp.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "resume/area", consumes = {MediaType.ALL_VALUE},
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class RestAreaResumeController {

//    @GetMapping("all-cities")   //+ массив городов с параметрами
//    public List<CityResumeFrontDTO> readAllCitiesResumeInfo() {
//        return areaService.createListAllCitiesInfo();
//    }

}
