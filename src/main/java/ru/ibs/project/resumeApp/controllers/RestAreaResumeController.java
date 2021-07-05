package ru.ibs.project.resumeApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ibs.project.resumeApp.dto.frontDTO.CityByResumesFrontDTO;
import ru.ibs.project.resumeApp.dto.frontDTO.RegionByResumesFrontDTO;
import ru.ibs.project.services.interfaces.AreaService;
import ru.ibs.project.vacancyApp.dto.frontDTO.RegionByVacanciesFrontDTO;

import java.util.List;

@RestController
@RequestMapping(value = "resume/area", consumes = {MediaType.ALL_VALUE},
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class RestAreaResumeController {

    @Autowired
    private AreaService areaService;

    @GetMapping("all-cities")
    public List<CityByResumesFrontDTO> readAllCitiesResumeInfo() {
        return areaService.createListAllCitiesInfoByResumes();
    }

    @GetMapping("all-regions")
    public List<RegionByResumesFrontDTO> readAllRegionsInfo() {
        return areaService.createListAllRegionsInfoByResumes();
    }


}
