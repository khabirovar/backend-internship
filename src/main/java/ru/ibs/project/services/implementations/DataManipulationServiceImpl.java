package ru.ibs.project.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import ru.ibs.project.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;
import ru.ibs.project.entities.Area;
import ru.ibs.project.entities.Vacancy;
import ru.ibs.project.entities.VacancyArea;
import ru.ibs.project.repositories.AreaRepository;
import ru.ibs.project.repositories.VacancyAreaRepository;
import ru.ibs.project.repositories.VacancyRepository;
import ru.ibs.project.services.interfaces.DataManipulationService;
import ru.ibs.project.services.interfaces.DownloadFromHHService;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class DataManipulationServiceImpl implements DataManipulationService {

    private final ConversionService conversionService;
    private VacancyRepository vacancyRepository;
    private AreaRepository areaRepository;
    private VacancyAreaRepository vacancyAreaRepository;
    private DownloadFromHHService downloadFromHHService;

    private Set<Area> areaSet = new LinkedHashSet<>();
    private Set<Vacancy> vacancySet = new LinkedHashSet<>();

    @Autowired
    public DataManipulationServiceImpl(
            @Qualifier("mvcConversionService") ConversionService conversionService,
            VacancyRepository vacancyRepository,
            AreaRepository areaRepository,
            VacancyAreaRepository vacancyAreaRepository,
            DownloadFromHHService downloadFromHHService
    ) {
        this.conversionService = conversionService; //+
        this.vacancyRepository = vacancyRepository; //+
        this.areaRepository = areaRepository;  //+
        this.vacancyAreaRepository = vacancyAreaRepository; //+
        this.downloadFromHHService = downloadFromHHService; //+
    }


    @Override
    @Transactional
    public void createAll(Set<ItemDTO> itemDTOs) {   //в data manipulation service
        log.info("start fetching areas and vacancies");
        itemDTOs.forEach(itemDTO -> {
            Area area = conversionService.convert(itemDTO, Area.class);
            areaSet.add(area);
            Vacancy vacancy = conversionService.convert(itemDTO, Vacancy.class);
            vacancySet.add(vacancy);
        });

        log.info("start add nameRegion to areas");
        areaSet.forEach(area -> {
            String nameRegion = downloadFromHHService.downloadNameRegionByNameCity(area.getNameCity());
            area.setNameRegion(nameRegion);
        });
        areaRepository.saveAll(areaSet);
        vacancyRepository.saveAll(vacancySet);
        log.info("successful fetching areas and vacancies");

        log.info("start fetching VacancyAreas");
        AtomicInteger i = new AtomicInteger();  //for log
        Set<VacancyArea> vacancyAreaSet = new LinkedHashSet<>(); //for unique vacancyAreas
        itemDTOs.forEach(itemDTO -> {
            VacancyArea vacancyArea = conversionService.convert(itemDTO, VacancyArea.class);
            vacancyAreaSet.add(vacancyArea);
            log.info("convert " + i.getAndIncrement() + " VacancyArea");
        });
        vacancyAreaRepository.saveAll(vacancyAreaSet);
        log.info("successful fetching VacancyAreas");
        areaSet.clear();
        vacancySet.clear();
        vacancyAreaSet.clear();
    }


    @Override
    @Transactional
    public void deleteVacancyArea() {   //в data manipulation service
        vacancyAreaRepository.deleteAll();
    }

    @Override
    @Transactional
    public void deleteAreaAndVacancy() {  //в data manipulation service
        vacancyRepository.deleteAll();
        areaRepository.deleteAll();
    }


    @Override
    public Set<Area> getAreaSet() {  //в data manipulation service
        return areaSet;
    }

    @Override
    public Set<Vacancy> getVacancySet() {  //в data manipulation service
        return vacancySet;
    }

    //        vacancyRepository.saveAll(itemDTOs.stream().
//                map(itemDTO -> conversionService.convert(itemDTO, Vacancy.class)).collect(Collectors.toList()));

}
