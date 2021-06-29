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

    private VacancyRepository vacancyRepository;

    private AreaRepository areaRepository;

    private VacancyAreaRepository vacancyAreaRepository;

    @Autowired
    public DeleteServiceImpl(VacancyRepository vacancyRepository,
                             AreaRepository areaRepository, VacancyAreaRepository vacancyAreaRepository) {
        this.vacancyRepository = vacancyRepository;
        this.areaRepository = areaRepository;
        this.vacancyAreaRepository = vacancyAreaRepository;
    }

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
