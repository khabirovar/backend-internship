package ru.ibs.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ibs.project.dto.frontDTO.CityFrontDTO;
import ru.ibs.project.dto.frontDTO.RegionFrontDTO;
import ru.ibs.project.entities.Area;
import ru.ibs.project.services.interfaces.AreaService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/area", consumes = {MediaType.ALL_VALUE},
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RestAreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping("allVacanciesInEachRegion") //считать все из БД +
    public List<RegionFrontDTO> readAllVacanciesInEachRegion() {
        areaService.createListRegionWithAllVacancies();


        List<RegionFrontDTO> list = new ArrayList<>();
        return list;
    }


    @GetMapping("allVacanciesInEachCity")   //происходит рекурсия в ответе
    public List<Area> readAllVacanciesInEachCity() {
       return areaService.createListCityWithAllVacancies();
    }

    @GetMapping("allCities")   //происходит рекурсия в ответе
    public List<CityFrontDTO> readAll() {
        return areaService.createListAllCities();
    }


}
