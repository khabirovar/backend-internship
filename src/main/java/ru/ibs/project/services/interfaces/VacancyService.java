package ru.ibs.project.services.interfaces;

import ru.ibs.project.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;
import ru.ibs.project.entities.Area;
import ru.ibs.project.entities.Vacancy;

import java.util.Set;

public interface VacancyService {

    void createAll(Set<ItemDTO> itemDTOs);

    Set<Area> getAreaSet();

    Set<Vacancy> getVacancySet();


}
