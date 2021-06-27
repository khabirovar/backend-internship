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
import ru.ibs.project.services.interfaces.RTService;
import ru.ibs.project.services.interfaces.VacancyService;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class VacancyServiceImpl implements VacancyService {

    private final ConversionService conversionService;
    private VacancyRepository vacancyRepository;
    private AreaRepository areaRepository;
    private VacancyAreaRepository vacancyAreaRepository;
    private final TransactionTemplate transactionTemplate;
    private Set<Area> areaSet = new LinkedHashSet<>();
    private Set<Vacancy> vacancySet = new LinkedHashSet<>();
    private RTService rtService;

    @Autowired
    public VacancyServiceImpl(
            @Qualifier("mvcConversionService") ConversionService conversionService,
            VacancyRepository vacancyRepository,
            AreaRepository areaRepository,
            VacancyAreaRepository vacancyAreaRepository,
            TransactionTemplate transactionTemplate,
            RTService rtService
    ) {
        this.conversionService = conversionService;
        this.vacancyRepository = vacancyRepository;
        this.areaRepository = areaRepository;
        this.vacancyAreaRepository = vacancyAreaRepository;
        this.transactionTemplate = transactionTemplate;
        this.rtService=rtService;
    }


    @Override
    @Transactional
    public void createAll(Set<ItemDTO> itemDTOs) {

        log.info("start fetching areas and vacancies");
        itemDTOs.forEach(itemDTO -> {
            Area area = conversionService.convert(itemDTO, Area.class);
            areaSet.add(area);
            Vacancy vacancy = conversionService.convert(itemDTO, Vacancy.class);
            vacancySet.add(vacancy);
        });

        log.info("start add nameRegion to areas");
        areaSet.forEach(area -> {
            String nameRegion = rtService.readRegion(area.getNameArea());
            area.setNameRegion(nameRegion);
        });
//        for (Area area : areaSet) {
//            String nameRegion = rtService.readRegion(area.getNameArea());
//            area.setNameRegion(nameRegion);
//        }
        areaRepository.saveAll(areaSet);
        vacancyRepository.saveAll(vacancySet);
        log.info("successful fetching areas and vacancies");
//        itemDTOs.forEach(itemDTO -> {
//            VacancyArea vacancyArea = conversionService.convert(itemDTO, VacancyArea.class);
//            vacancyAreaRepository.save(vacancyArea);
//        });
        log.info("start fetching VacancyAreas");
        AtomicInteger i = new AtomicInteger();  //for log
        Set<VacancyArea> vacancyAreaSet = new LinkedHashSet<>(); //for unique vacancyAreas
        itemDTOs.forEach(itemDTO -> {
            VacancyArea vacancyArea = conversionService.convert(itemDTO, VacancyArea.class);
            vacancyAreaSet.add(vacancyArea);
            log.info("convert " + i.getAndIncrement() + " VacancyArea");
        });
//        for (ItemDTO itemDTO : itemDTOs) {
//            VacancyArea vacancyArea = conversionService.convert(itemDTO, VacancyArea.class);
////            vacancyAreaRepository.save(vacancyArea);
//            vacancyAreaSet.add(vacancyArea);
//            System.out.println(i++);
//        }
        vacancyAreaRepository.saveAll(vacancyAreaSet);
        log.info("successful fetching VacancyAreas");
        areaSet.clear();
        vacancySet.clear();
        vacancyAreaSet.clear();
    }

    @Override
    public Set<Area> getAreaSet() {
        return areaSet;
    }

    @Override
    public Set<Vacancy> getVacancySet() {
        return vacancySet;
    }

    //        vacancyRepository.saveAll(itemDTOs.stream().
//                map(itemDTO -> conversionService.convert(itemDTO, Vacancy.class)).collect(Collectors.toList()));

}
