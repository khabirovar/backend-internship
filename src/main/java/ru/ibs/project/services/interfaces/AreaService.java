package ru.ibs.project.services.interfaces;

import ru.ibs.project.dto.frontDTO.CityFrontDTO;
import ru.ibs.project.dto.frontDTO.RegionFrontDTO;
import ru.ibs.project.entities.Area;

import java.util.List;

public interface AreaService {
    List<RegionFrontDTO> createListRegionWithAllVacancies();

//    List<Area> readAllVacanciesInEachCity();

    List<Area> createListCityWithAllVacancies();


    List<CityFrontDTO> createListAllCities();
}
