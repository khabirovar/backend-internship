package ru.ibs.project.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ibs.project.dto.frontDTO.CityFrontDTO;
import ru.ibs.project.dto.frontDTO.RegionFrontDTO;
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


    @Override
    public List<RegionFrontDTO> createListAllRegionsInfo() {
        Set<String> allRegions = areaRepository.readDistinctByNameRegion();
        List<RegionFrontDTO> regionFrontDTOList = new ArrayList<>();
        for (String region : allRegions) {
            Long medianValue = null;
            Long fromSalary = null;
            Long toSalary = null;
            Long countVacancies = 0L;
            List<Area> areas = areaRepository.findAllByNameRegion(region);
            Long countCities = areas.stream().count();
            List<Long> listSalary = new ArrayList<>();
            for (Area area : areas) {
                countVacancies += area.getVacancyAreas().stream().count();
                createListSalary(area, listSalary);
            }
            if (!listSalary.isEmpty()) {
                medianValue = findMedianValueByListSalary(listSalary);
                fromSalary = Collections.min(listSalary);
                toSalary = Collections.max(listSalary);
            }
            regionFrontDTOList.add(new RegionFrontDTO(region, countCities, countVacancies, fromSalary, toSalary, medianValue));
        }
        log.info("regions: " + regionFrontDTOList.size());
        return regionFrontDTOList;
    }


    @Override
    public List<CityFrontDTO> createListAllCitiesInfo() {  //+
        List<Area> areaList = (List<Area>) areaRepository.findAll();
        List<CityFrontDTO> cityFrontDTOList = new ArrayList<>();
        for (Area area : areaList) {
            Long medianValue = null;
            Long fromSalary = null;
            Long toSalary = null;
            String nameCity = area.getNameCity();
            String nameRegion = area.getNameRegion();
            Long countVacancies = area.getVacancyAreas().stream().count();
            List<Long> listSalary = new ArrayList<>();
            createListSalary(area, listSalary);
            if (!listSalary.isEmpty()) {
                medianValue = findMedianValueByListSalary(listSalary);
                fromSalary = Collections.min(listSalary);
                toSalary = Collections.max(listSalary);
            }
            cityFrontDTOList.add(new CityFrontDTO(nameCity, nameRegion, countVacancies, fromSalary, toSalary, medianValue));
        }
        log.info("cities " + cityFrontDTOList.size());
        return cityFrontDTOList;
    }

    private void createListSalary(Area area, List<Long> listSalary) {
        Set<VacancyArea> vacancyAreaSet = area.getVacancyAreas();
        for (VacancyArea vacancyArea : vacancyAreaSet) {
            if (vacancyArea.getFromSalary() != null) {
                Long fromSalaryRUB = convertCurrencyToRUR(vacancyArea.getCurrencySalary(), vacancyArea.getFromSalary());
                listSalary.add(fromSalaryRUB);
            }
            if (vacancyArea.getToSalary() != null) {
                Long toSalaryRUB = convertCurrencyToRUR(vacancyArea.getCurrencySalary(), vacancyArea.getToSalary());
                listSalary.add(toSalaryRUB);
            }
        }
    }


    @Override
    public List<Area> createListCityWithRegion() {  //+
        List<Area> listCityWithRegion = (List<Area>) areaRepository.findAll();
        log.info("cities " + listCityWithRegion.size());
        return listCityWithRegion;
    }


    private Long findMedianValueByListSalary(List<Long> listSalary) {
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
