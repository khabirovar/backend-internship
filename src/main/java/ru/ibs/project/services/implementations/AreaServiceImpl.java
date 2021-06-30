package ru.ibs.project.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ibs.project.dto.frontDTO.CityFrontDTO;
import ru.ibs.project.entities.Area;
import ru.ibs.project.entities.VacancyArea;
import ru.ibs.project.repositories.AreaRepository;
import ru.ibs.project.services.interfaces.AreaService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;


//    //создание листа всех регионов со всеми вакансиями
//    @Override
//    public List<RegionFrontDTO> createListRegionWithAllVacancies() {
//        //этот лис заполнить и вернуть в методе
//        List<RegionFrontDTO> listRegion = new ArrayList<>();
//
//        //этот лист получен с БД
//        Iterable<Area> listArea = areaRepository.findAll();
//
//        int i = 1;
//        for (Area area : listArea) {
//            /*
//            составить сет
//
//
//            1 - создать лист RegionFrontDTO и занести в него все названия регионов
//            будет список всех областей
//
//            2 создавать VacancyFrontDTO и добавлять их в лист
//            2.1 найти в табл area, город, который соответствует первому городу из
//            списка List<RegionFrontDTO>
//            VacancyFrontDTO
//
//
//            2 - составить List<VacancyFrontDTO> по каждому региону:
//            добовлять в List<VacancyFrontDTO> те вакансии, у которых id_area
//             */
////            String nameRegion = area.getNameRegion();
////            RegionFrontDTO regionFrontDTO = new RegionFrontDTO();
////            List<VacancyFrontDTO> vacancyFrontDTOList = new ArrayList<>();
////            VacancyFrontDTO vacancyFrontDTO = new VacancyFrontDTO();
//////            vacancyFrontDTO.setCurrencySalary();
////            regionFrontDTO.setRegion(nameRegion);
//            System.out.println(area.getNameCity());
//            System.out.println(area.getNameRegion());
//            System.out.println(i++);
//            //сформировать region
////            listRegion.add()
////            System.out.println(area.getVacancyAreas());
//        }
//
//        List<RegionFrontDTO> listRegion = new ArrayList<>();
//
//        return listRegion;
//    }


    @Override
    public List<Area> createListCityWithRegion() {  //++
        List<Area> listCityWithRegion = (List<Area>) areaRepository.findAll();
        log.info("cities " + listCityWithRegion.size());
        return listCityWithRegion;
    }


    @Override
    public List<CityFrontDTO> createListAllCitiesInfo() {  //++
        List<Area> areaList = (List<Area>) areaRepository.findAll();
        List<CityFrontDTO> cityFrontDTOList = new ArrayList<>();
        for (Area area : areaList) {
            String nameCity = area.getNameCity();
            String nameRegion = area.getNameRegion();
            Long countVacancies = area.getVacancyAreas().stream().count();
            List<Long> listSalary = new ArrayList<>();

            Set<VacancyArea> vacancyAreaSet = area.getVacancyAreas();
            Long fromSalary = Long.MAX_VALUE;
            Long toSalary = Long.MIN_VALUE;
            for (VacancyArea vacancyArea : vacancyAreaSet) {
                if (vacancyArea.getFromSalary() != null) {
                    Long fromSalaryRUB =
                            convertCurrencyToRUR(vacancyArea.getCurrencySalary(), vacancyArea.getFromSalary());
                    listSalary.add(fromSalaryRUB);
                    if (fromSalaryRUB < fromSalary)
                        fromSalary = fromSalaryRUB;
                }
                if (vacancyArea.getToSalary() != null) {
                    Long toSalaryRUB =
                            convertCurrencyToRUR(vacancyArea.getCurrencySalary(), vacancyArea.getToSalary());
                    listSalary.add(toSalaryRUB);
                    if (toSalaryRUB > toSalary)
                        toSalary = toSalaryRUB;
                }
            }
            if (fromSalary == Long.MAX_VALUE)
                fromSalary = null;
            if (toSalary == Long.MIN_VALUE)
                toSalary = null;

            //private метод findMedianValueByListSalary
//            Long medianValue = null;
//            if (!listSalary.isEmpty()) {
//                listSalary.sort(Comparator.naturalOrder());
//                if (listSalary.size() == 1)
//                    medianValue = listSalary.get(0);
//                else if ((listSalary.size() % 2) == 0) {
//                    int aIndex = listSalary.size() / 2 - 1;
//                    int bIndex = listSalary.size() / 2;
//                    Long aValue = listSalary.get(aIndex);
//                    Long bValue = listSalary.get(bIndex);
//                    medianValue = (aValue + bValue) / 2;
//                } else {
//                    int i = listSalary.size() / 2;
//                    medianValue = listSalary.get(i);
//                }
//            }
            //////////////////////////////
            Long medianValue = findMedianValueByListSalary(listSalary);

            cityFrontDTOList.add(new CityFrontDTO(nameCity, nameRegion, countVacancies, fromSalary, toSalary, medianValue));
        }
        log.info("cities " + cityFrontDTOList.size());
        return cityFrontDTOList;
    }

    private Long findMedianValueByListSalary(List<Long> listSalary) {
//        Long medianValue = null;
        if (listSalary.isEmpty())
            return null;
        else {
            int sizeListSalary = listSalary.size();
            listSalary.sort(Comparator.naturalOrder());
            if (sizeListSalary == 1)
                return listSalary.get(0);
            else if ((sizeListSalary % 2) == 0) {
                int aIndex = sizeListSalary / 2 - 1;
                int bIndex = sizeListSalary / 2;
                Long aValue = listSalary.get(aIndex);
                Long bValue = listSalary.get(bIndex);
                return (aValue + bValue) / 2;
            } else {
                int i = sizeListSalary / 2;
                return listSalary.get(i);
            }
        }
//        return null;
    }

    private Long convertCurrencyToRUR(String currency, Long value) {
        switch (currency) {
            case "BYR":
                return (long) (value * 28.65);
            case "KZT":
                return (long) (value * 0.17);
            case "EUR":
                return (long) (value * 86.27);
            case "UAH":
                return (long) (value * 2.65);
            case "USD":
                return (long) (value * 72.45);
            default:
                return value;
        }
    }
}
