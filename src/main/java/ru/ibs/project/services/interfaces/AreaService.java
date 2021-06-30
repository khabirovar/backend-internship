package ru.ibs.project.services.interfaces;

import ru.ibs.project.dto.frontDTO.CityFrontDTO;
import ru.ibs.project.entities.Area;

import java.util.List;

public interface AreaService {

    List<Area> createListCityWithRegion();

    List<CityFrontDTO> createListAllCitiesInfo();
}
