package ru.ibs.project.vacancyApp.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ibs.project.vacancyApp.ConvertItemDTOCallableClass;
import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.AreaDTO;
import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;
import ru.ibs.project.vacancyApp.dto.hhDTO.respDictionaryDTOs.RegionDTO;
import ru.ibs.project.vacancyApp.entities.Area;
import ru.ibs.project.vacancyApp.entities.Vacancy;
import ru.ibs.project.vacancyApp.entities.VacancyArea;
import ru.ibs.project.vacancyApp.repositories.AreaRepository;
import ru.ibs.project.vacancyApp.repositories.VacancyAreaRepository;
import ru.ibs.project.vacancyApp.repositories.VacancyRepository;
import ru.ibs.project.vacancyApp.services.interfaces.DataManipulationService;
import ru.ibs.project.vacancyApp.services.interfaces.DownloadFromHHService;

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
    private DownloadFromHHService downloadFromHHService;

    private Set<Area> areaSet = new LinkedHashSet<>();  //for using in ItemDtoToVacancyArea
    private Set<Vacancy> vacancySet = new LinkedHashSet<>(); //for using in ItemDtoToVacancyArea

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
    public void createAll(Set<ItemDTO> itemDTOs) throws ExecutionException, InterruptedException {
        log.info("start fetching areas and vacancies");
        itemDTOs.forEach(itemDTO -> {
            Area area = conversionService.convert(itemDTO, Area.class);
            areaSet.add(area);
            Vacancy vacancy = conversionService.convert(itemDTO, Vacancy.class);
            vacancySet.add(vacancy);
        });

        log.info("start add nameRegion to areas");
////////////////////////////////////////////////////////////////
        //разбить на несколько потоков ?????
        //этот кусок кода должны исполнять несколько потоков
//
//        AtomicInteger k = new AtomicInteger();  //for mapItems
//        Map<Integer, Area> mapAreas = new HashMap<>();
//        for (Area area : areaSet) {
//            mapAreas.put(k.getAndIncrement(), area);
//        }
//
//        ExecutorService esArea = Executors.newFixedThreadPool(5); //над задачей будут работать 4 потока
//        for (int index = 0; index < mapAreas.size(); /*index++*/) {
//
//
//                mapAreas.get(index++).getNameCity() ->
//
//            //
//
//        }
//
//        esArea.shutdown();

        List<RegionDTO> regionDTOList = downloadFromHHService.downloadRegionDTOList();
        areaSet.forEach(area -> {
            String nameRegion = findNameRegionByNameCity(area.getNameCity(),regionDTOList);
            area.setNameRegion(nameRegion);
        });


//
//        areaSet.forEach(area -> {
//            String nameRegion = downloadFromHHService.downloadNameRegionByNameCity(area.getNameCity());
//            area.setNameRegion(nameRegion);
//        });
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        areaRepository.saveAll(areaSet);
        vacancyRepository.saveAll(vacancySet);
        log.info("successful fetching areas and vacancies");

        log.info("start fetching VacancyAreas");
        Set<VacancyArea> vacancyAreaSet = fillingVacancyAreaSet(itemDTOs);

//        itemDTOs.forEach(itemDTO -> {
//            VacancyArea vacancyArea = conversionService.convert(itemDTO, VacancyArea.class);
//            vacancyAreaSet.add(vacancyArea);
//            log.info("convert " + i.getAndIncrement() + " VacancyArea");
//        });

        vacancyAreaRepository.saveAll(vacancyAreaSet);
        log.info("successful fetching VacancyAreas");
        regionDTOList.clear();
        areaSet.clear();
        vacancySet.clear();
        vacancyAreaSet.clear();
    }

    private String findNameRegionByNameCity(String nameCity, List<RegionDTO> regionDTOList){
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
        for (int index = 0; index < mapItems.size();) {
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
    public void deleteVacancyArea() {   //в data manipulation service
        vacancyAreaRepository.deleteAll();
    }

    @Override
    @Transactional
    public void deleteAreaAndVacancy() {
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
