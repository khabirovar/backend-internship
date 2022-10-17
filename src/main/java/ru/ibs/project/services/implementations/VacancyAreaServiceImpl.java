package ru.ibs.project.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ibs.project.entities.VacancyArea;
import ru.ibs.project.repositories.VacancyAreaRepository;
import ru.ibs.project.services.interfaces.VacancyAreaService;

import java.util.List;

@Service
@Slf4j
public class VacancyAreaServiceImpl implements VacancyAreaService {

    private VacancyAreaRepository vacancyAreaRepository;

    @Autowired
    public VacancyAreaServiceImpl(VacancyAreaRepository vacancyAreaRepository) {
        this.vacancyAreaRepository = vacancyAreaRepository;
    }

    @Override
    public List<VacancyArea> createAllVacancyAreaList() {   //all vacancies array
        List<VacancyArea> allVacancies = (List<VacancyArea>) vacancyAreaRepository.findAll();
        log.info("size allVacancies:" + allVacancies.size());
        return allVacancies;
    }
}
