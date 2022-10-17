package ru.ibs.project.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ibs.project.entities.Area;
import ru.ibs.project.entities.Resume;
import ru.ibs.project.entities.Vacancy;
import ru.ibs.project.entities.VacancyArea;
import ru.ibs.project.repositories.AreaRepository;
import ru.ibs.project.repositories.ResumeRepository;
import ru.ibs.project.repositories.VacancyAreaRepository;
import ru.ibs.project.repositories.VacancyRepository;
import ru.ibs.project.resumeApp.dto.hhDTO.respResumeDTOs.ResumeDTO;
import ru.ibs.project.services.interfaces.DataManipulationService;
import ru.ibs.project.services.interfaces.DownloadFromHHServiceVacancy;
import ru.ibs.project.vacancyApp.converter.ConvertItemDTOCallableClass;
import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.AreaDTO;
import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;
import ru.ibs.project.vacancyApp.dto.hhDTO.respDictionaryDTOs.RegionDTO;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class DataManipulationServiceImpl implements DataManipulationService {

    private final ConversionService conversionService;
    private VacancyRepository vacancyRepository;
    private AreaRepository areaRepository;
    private VacancyAreaRepository vacancyAreaRepository;
    private DownloadFromHHServiceVacancy downloadFromHHServiceVacancy;
    private ResumeRepository resumeRepository;


    private Set<Area> areaSet = new LinkedHashSet<>();  //for using in ItemDtoToVacancyArea
    private Set<Vacancy> vacancySet = new LinkedHashSet<>(); //for using in ItemDtoToVacancyArea

    @Autowired
    public DataManipulationServiceImpl(
            @Qualifier("mvcConversionService") ConversionService conversionService,
            VacancyRepository vacancyRepository,
            AreaRepository areaRepository,
            VacancyAreaRepository vacancyAreaRepository,
            DownloadFromHHServiceVacancy downloadFromHHServiceVacancy,
            ResumeRepository resumeRepository
    ) {
        this.conversionService = conversionService;
        this.vacancyRepository = vacancyRepository;
        this.areaRepository = areaRepository;
        this.vacancyAreaRepository = vacancyAreaRepository;
        this.downloadFromHHServiceVacancy = downloadFromHHServiceVacancy;
        this.resumeRepository = resumeRepository;
    }


    @Override
    @Transactional
    public void createAllVacancies(Set<ItemDTO> itemDTOs) throws ExecutionException, InterruptedException {
        log.info("start fetching areas and vacancies");
        areaSet.clear();
        vacancySet.clear();
        itemDTOs.forEach(itemDTO -> {
            Area area = conversionService.convert(itemDTO, Area.class);
            areaSet.add(area);
            Vacancy vacancy = conversionService.convert(itemDTO, Vacancy.class);
            vacancySet.add(vacancy);
        });
        fillingAreaSetByRegion();
        areaRepository.saveAll(areaSet);
        vacancyRepository.saveAll(vacancySet);
        log.info("successful fetching areas and vacancies");
        log.info("start fetching VacancyAreas");
        Set<VacancyArea> vacancyAreaSet = fillingVacancyAreaSet(itemDTOs);
        vacancyAreaRepository.saveAll(vacancyAreaSet);
        log.info("successful fetching VacancyAreas");
        areaSet.clear();
        vacancySet.clear();
        vacancyAreaSet.clear();
    }


    @Override
    @Transactional
    public void createAllResumes(Set<ResumeDTO> resumeDTOSet) {
        log.info("start fetching resume");
        areaSet.clear();
        resumeDTOSet.forEach(resumeDTO -> {
            Area area = conversionService.convert(resumeDTO, Area.class);
            areaSet.add(area);
        });
        fillingAreaSetByRegion();
        areaRepository.saveAll(areaSet);
        log.info("successful fetching area");
        log.info("start fetching Resume");
        Set<Resume> resumeSet = new LinkedHashSet<>();
        resumeDTOSet.forEach(resumeDTO -> {
            Resume resume = conversionService.convert(resumeDTO, Resume.class);
            resumeSet.add(resume);
        });
        resumeRepository.saveAll(resumeSet);
        areaSet.clear();
        resumeSet.clear();
        log.info("successful fetching Resume");
    }

    private void fillingAreaSetByRegion() {
        log.info("start add nameRegion to areas");
        List<RegionDTO> regionDTOList = downloadFromHHServiceVacancy.downloadRegionDTOList();
        areaSet.forEach(area -> {
            String nameRegion = findNameRegionByNameCity(area.getNameCity(), regionDTOList);
            area.setNameRegion(nameRegion);
        });
        regionDTOList.clear();
    }


    private String findNameRegionByNameCity(String nameCity, List<RegionDTO> regionDTOList) {
        if (nameCity.equals("Санкт-Петербург") || nameCity.equals("Москва"))
            return nameCity;
        String nameRegion = "Вне РФ";
        for (RegionDTO regionDTO : regionDTOList) {
            List<AreaDTO> cities = regionDTO.getAreas();
            for (AreaDTO city : cities) {
                if (city.getName().equals(nameCity))
                    return regionDTO.getName();
            }
        }
        return nameRegion;
    }


    private Set<VacancyArea> fillingVacancyAreaSet(Set<ItemDTO> itemDTOs) throws ExecutionException, InterruptedException {
        Set<VacancyArea> vacancyAreaSet = new LinkedHashSet<>(); //for unique vacancyAreas
        AtomicInteger j = new AtomicInteger();  //for mapItems
        AtomicInteger i = new AtomicInteger();  //for log
        Map<Integer, ItemDTO> mapItems = new HashMap<>();
        for (ItemDTO itemDTO : itemDTOs) {
            mapItems.put(j.getAndIncrement(), itemDTO);
        }
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int index = 0; index < mapItems.size(); ) {
            Future<VacancyArea> vacancyAreaFuture1 = es.submit(new ConvertItemDTOCallableClass(conversionService, mapItems.get(index++)));
            Future<VacancyArea> vacancyAreaFuture2 = es.submit(new ConvertItemDTOCallableClass(conversionService, mapItems.get(index++)));
            Future<VacancyArea> vacancyAreaFuture3 = es.submit(new ConvertItemDTOCallableClass(conversionService, mapItems.get(index++)));
            Future<VacancyArea> vacancyAreaFuture4 = es.submit(new ConvertItemDTOCallableClass(conversionService, mapItems.get(index++)));
            Future<VacancyArea> vacancyAreaFuture5 = es.submit(new ConvertItemDTOCallableClass(conversionService, mapItems.get(index++)));
            Future<VacancyArea> vacancyAreaFuture6 = es.submit(new ConvertItemDTOCallableClass(conversionService, mapItems.get(index++)));
            Future<VacancyArea> vacancyAreaFuture7 = es.submit(new ConvertItemDTOCallableClass(conversionService, mapItems.get(index++)));
            Future<VacancyArea> vacancyAreaFuture8 = es.submit(new ConvertItemDTOCallableClass(conversionService, mapItems.get(index++)));
            Future<VacancyArea> vacancyAreaFuture9 = es.submit(new ConvertItemDTOCallableClass(conversionService, mapItems.get(index++)));
            Future<VacancyArea> vacancyAreaFuture10 = es.submit(new ConvertItemDTOCallableClass(conversionService, mapItems.get(index++)));
            if (vacancyAreaFuture1.get() != null)
                vacancyAreaSet.add(vacancyAreaFuture1.get());
            if (vacancyAreaFuture2.get() != null)
                vacancyAreaSet.add(vacancyAreaFuture2.get());
            if (vacancyAreaFuture3.get() != null)
                vacancyAreaSet.add(vacancyAreaFuture3.get());
            if (vacancyAreaFuture4.get() != null)
                vacancyAreaSet.add(vacancyAreaFuture4.get());
            if (vacancyAreaFuture5.get() != null)
                vacancyAreaSet.add(vacancyAreaFuture5.get());
            if (vacancyAreaFuture6.get() != null)
                vacancyAreaSet.add(vacancyAreaFuture6.get());
            if (vacancyAreaFuture7.get() != null)
                vacancyAreaSet.add(vacancyAreaFuture7.get());
            if (vacancyAreaFuture8.get() != null)
                vacancyAreaSet.add(vacancyAreaFuture8.get());
            if (vacancyAreaFuture9.get() != null)
                vacancyAreaSet.add(vacancyAreaFuture9.get());
            if (vacancyAreaFuture10.get() != null)
                vacancyAreaSet.add(vacancyAreaFuture10.get());
            log.info("convert " + i.getAndIncrement() * 10 + " VacancyArea");
        }
        es.shutdown();
        return vacancyAreaSet;
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

    @Override
    @Transactional
    public void deleteResume() {
        resumeRepository.deleteAll();
    }

    @Override
    public Set<Area> getAreaSet() {
        return areaSet;
    }

    @Override
    public Set<Vacancy> getVacancySet() {
        return vacancySet;
    }
}
