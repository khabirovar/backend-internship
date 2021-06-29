package ru.ibs.project.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ibs.project.dto.frontDTO.CityFrontDTO;
import ru.ibs.project.dto.frontDTO.RegionFrontDTO;
import ru.ibs.project.dto.frontDTO.VacancyFrontDTO;
import ru.ibs.project.entities.Area;
import ru.ibs.project.entities.VacancyArea;
import ru.ibs.project.repositories.AreaRepository;
import ru.ibs.project.services.interfaces.AreaService;

import java.util.*;

@Service
@Slf4j
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;


    //создание листа всех регионов со всеми вакансиями
    @Override
    public List<RegionFrontDTO> createListRegionWithAllVacancies() {
        //этот лис заполнить и вернуть в методе
        List<RegionFrontDTO> listRegion = new ArrayList<>();

        //этот лист получен с БД
        Iterable<Area> listArea = areaRepository.findAll();

        int i = 1;
        for (Area area : listArea) {
            /*
            составить сет


            1 - создать лист RegionFrontDTO и занести в него все названия регионов
            будет список всех областей

            2 создавать VacancyFrontDTO и добавлять их в лист
            2.1 найти в табл area, город, который соответствует первому городу из
            списка List<RegionFrontDTO>
            VacancyFrontDTO


            2 - составить List<VacancyFrontDTO> по каждому региону:
            добовлять в List<VacancyFrontDTO> те вакансии, у которых id_area
             */
//            String nameRegion = area.getNameRegion();
//            RegionFrontDTO regionFrontDTO = new RegionFrontDTO();
//            List<VacancyFrontDTO> vacancyFrontDTOList = new ArrayList<>();
//            VacancyFrontDTO vacancyFrontDTO = new VacancyFrontDTO();
////            vacancyFrontDTO.setCurrencySalary();
//            regionFrontDTO.setRegion(nameRegion);
            System.out.println(area.getNameArea());
            System.out.println(area.getNameRegion());
            System.out.println(i++);
            //сформировать region
//            listRegion.add()
//            System.out.println(area.getVacancyAreas());
        }

//        List<RegionFrontDTO> listRegion = new ArrayList<>();

        return listRegion;
    }


    @Override
    public List<Area> createListCityWithAllVacancies() {
        Iterable<Area> listArea = areaRepository.findAll();
        for (Area area : listArea) {

        }

        return (List<Area>) areaRepository.findAll();
    }


    @Override
    public List<CityFrontDTO> createListAllCities() {
        List<Area> areaList = (List<Area>) areaRepository.findAll();
        List<CityFrontDTO> cityFrontDTOList = new ArrayList<>();
        for (Area area : areaList) {
            String nameCity = area.getNameArea();
            String nameRegion = area.getNameRegion();
            Long countVacancies = area.getVacancyAreas().stream().count();
            List<Long> listSalary = new ArrayList<>();

            Set<VacancyArea> vacancyAreaSet = area.getVacancyAreas();
            Long fromSalary = Long.MAX_VALUE;
            Long toSalary = Long.MIN_VALUE;
            for (VacancyArea vacancyArea : vacancyAreaSet) {
                if (vacancyArea.getFromSalary() != null) {
                    listSalary.add(vacancyArea.getFromSalary());
                    if (vacancyArea.getFromSalary() < fromSalary)
                        fromSalary = vacancyArea.getFromSalary();
                }
                if (vacancyArea.getToSalary() != null) {
                    listSalary.add(vacancyArea.getToSalary());
                    if (vacancyArea.getToSalary() > toSalary)
                        toSalary = vacancyArea.getToSalary();
                }
            }
//            VacancyArea minBySalary = area.getVacancyAreas()
//                    .stream()
////                    .filter(e -> e.getFromSalary() != null)
//                    .min()
//                    .min(Comparator.comparing(VacancyArea::getToSalary))
//                    .orElse(null);


//            Long minSalary = minBySalary.getFromSalary();
//            System.out.println(minBySalary.getFromSalary());

//            VacancyArea maxBySalary = area.getVacancyAreas()
//                    .stream()
//                    .max(Comparator.comparing(VacancyArea::getToSalary))
//                    .orElseThrow(NoSuchElementException::new);

//            Long maxSalary = maxBySalary.getFromSalary();
            if (fromSalary == Long.MAX_VALUE)
                fromSalary = null;
            if (toSalary == Long.MIN_VALUE)
                toSalary = null;
            Long medianValue = null;

            if (!listSalary.isEmpty()) {
                listSalary.sort(Comparator.naturalOrder());
                if (listSalary.size() == 1)
                    medianValue = listSalary.get(0);  //или max или min
                else if ((listSalary.size() % 2) == 0) { //четное количество
                    // 1, 3, 4 ,5, 8, 9       (4+5)/2 = 4.5  //полусумма двух средних
                    int aIndex = listSalary.size() / 2 - 1;
                    int bIndex = listSalary.size() / 2;
                    Long aValue = listSalary.get(aIndex);
                    Long bValue = listSalary.get(bIndex);
                    medianValue = (aValue + bValue) / 2;
                } else {
                    int i = listSalary.size() / 2;
                    medianValue = listSalary.get(i);
                }
            }
            //заполнить медианное значение
            //https://allcalc.ru/node/1834
            //заполнить лист всеми значениями не null и
            // из этого листа расчитать медиану


            cityFrontDTOList.add(new CityFrontDTO(nameCity, nameRegion, countVacancies, fromSalary, toSalary, medianValue));
        }
        return cityFrontDTOList;
    }
}
