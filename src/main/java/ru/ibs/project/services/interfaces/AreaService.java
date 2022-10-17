package ru.ibs.project.services.interfaces;

import ru.ibs.project.entities.Area;
import ru.ibs.project.resumeApp.dto.frontDTO.CityByResumesFrontDTO;
import ru.ibs.project.resumeApp.dto.frontDTO.RegionByResumesFrontDTO;
import ru.ibs.project.vacancyApp.dto.frontDTO.CityByVacanciesFrontDTO;
import ru.ibs.project.vacancyApp.dto.frontDTO.RegionByVacanciesFrontDTO;

import java.util.List;

public interface AreaService {

    List<Area> createListCityWithRegion();

    List<CityByVacanciesFrontDTO> createListAllCitiesInfoByVacancies();

    List<RegionByVacanciesFrontDTO> createListAllRegionsInfoByVacancies();

    List<CityByResumesFrontDTO> createListAllCitiesInfoByResumes();

    List<RegionByResumesFrontDTO> createListAllRegionsInfoByResumes();

}
