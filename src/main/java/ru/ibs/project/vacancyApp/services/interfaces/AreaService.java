package ru.ibs.project.vacancyApp.services.interfaces;

import ru.ibs.project.vacancyApp.entities.Area;
import ru.ibs.project.vacancyApp.dto.frontDTO.CityFrontDTO;
import ru.ibs.project.vacancyApp.dto.frontDTO.RegionFrontDTO;

import java.util.List;

public interface AreaService {

    List<Area> createListCityWithRegion();

    List<CityFrontDTO> createListAllCitiesInfo();

    List<RegionFrontDTO> createListAllRegionsInfo();
}
