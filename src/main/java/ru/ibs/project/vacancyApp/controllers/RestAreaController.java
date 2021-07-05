package ru.ibs.project.vacancyApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ibs.project.entities.Area;
import ru.ibs.project.services.interfaces.AreaService;
import ru.ibs.project.vacancyApp.dto.frontDTO.CityByVacanciesFrontDTO;
import ru.ibs.project.vacancyApp.dto.frontDTO.RegionByVacanciesFrontDTO;

import java.util.List;

@RestController
@RequestMapping(value = "/area", consumes = {MediaType.ALL_VALUE},
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class RestAreaController {

    private AreaService areaService;

    @Autowired
    public RestAreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping("all-cities-with-region")   //just all cities array with region
    public List<Area> readAllCitiesWithRegion() {
        return areaService.createListCityWithRegion();
    }

    @GetMapping("all-cities")   //array all cities with params
    public List<CityByVacanciesFrontDTO> readAllCitiesInfo() {
        return areaService.createListAllCitiesInfoByVacancies();
    }

    @GetMapping("all-regions")   //array all regions with params
    public List<RegionByVacanciesFrontDTO> readAllRegionsInfo() {
        return areaService.createListAllRegionsInfoByVacancies();
    }
}
