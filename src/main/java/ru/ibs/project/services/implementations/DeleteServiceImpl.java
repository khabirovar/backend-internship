package ru.ibs.project.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ibs.project.repositories.AreaRepository;
import ru.ibs.project.repositories.VacancyAreaRepository;
import ru.ibs.project.repositories.VacancyRepository;
import ru.ibs.project.services.interfaces.DeleteService;

@Service
public class DeleteServiceImpl implements DeleteService {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private VacancyAreaRepository vacancyAreaRepository;

    @Override
    @Transactional
    public void deleteVacancyArea() {
        vacancyAreaRepository.deleteAll();
    }

    @Override
    @Transactional
    public void deleteAreaAndVacancy() {
        vacancyRepository.deleteAll();
        areaRepository.deleteAll();
    }
}
