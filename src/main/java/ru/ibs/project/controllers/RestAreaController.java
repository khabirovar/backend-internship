package ru.ibs.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ibs.project.dto.frontDTO.CityFrontDTO;
import ru.ibs.project.entities.Area;
import ru.ibs.project.services.interfaces.AreaService;

import java.util.List;

@RestController
@RequestMapping(value = "/area", consumes = {MediaType.ALL_VALUE},
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RestAreaController {

    @Autowired
    private AreaService areaService;


    @GetMapping("all-cities-with-region")   //+ массив городов с указанием региона
    public List<Area> readAllCitiesWithRegion() {
       return areaService.createListCityWithRegion();
    }

    @GetMapping("all-cities")   //+ массив городов с параметрами
    public List<CityFrontDTO> readAllCitiesInfo() {
        return areaService.createListAllCitiesInfo();
    }
}
