package ru.ibs.project.vacancyApp.services.interfaces;

import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;
import ru.ibs.project.vacancyApp.entities.Area;
import ru.ibs.project.vacancyApp.entities.Vacancy;

import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface DataManipulationService {

    void createAll(Set<ItemDTO> itemDTOs) throws ExecutionException, InterruptedException;

    Set<Area> getAreaSet();

    Set<Vacancy> getVacancySet();

    void deleteVacancyArea();

    void deleteAreaAndVacancy();
}
