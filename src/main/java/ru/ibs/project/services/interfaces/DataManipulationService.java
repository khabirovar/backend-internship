package ru.ibs.project.services.interfaces;

import ru.ibs.project.entities.Area;
import ru.ibs.project.entities.Vacancy;
import ru.ibs.project.resumeApp.dto.hhDTO.respResumeDTOs.ResumeDTO;
import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;

import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface DataManipulationService {

    void createAllVacancies(Set<ItemDTO> itemDTOs) throws ExecutionException, InterruptedException;

    Set<Area> getAreaSet();

    Set<Vacancy> getVacancySet();

    void deleteVacancyArea();

    void deleteAreaAndVacancy();

    void deleteResume();

    void createAllResumes(Set<ResumeDTO> resumeDTOSet);

}
