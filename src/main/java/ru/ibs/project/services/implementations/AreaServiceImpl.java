package ru.ibs.project.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ibs.project.entities.Area;
import ru.ibs.project.entities.Resume;
import ru.ibs.project.entities.VacancyArea;
import ru.ibs.project.repositories.AreaRepository;
import ru.ibs.project.resumeApp.dto.frontDTO.CityByResumesFrontDTO;
import ru.ibs.project.resumeApp.dto.frontDTO.RegionByResumesFrontDTO;
import ru.ibs.project.services.interfaces.AreaService;
import ru.ibs.project.vacancyApp.dto.frontDTO.CityByVacanciesFrontDTO;
import ru.ibs.project.vacancyApp.dto.frontDTO.RegionByVacanciesFrontDTO;

import java.util.*;

@Service
@Slf4j
public class AreaServiceImpl implements AreaService {

    private AreaRepository areaRepository;

    @Autowired
    public AreaServiceImpl(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Override
    public List<RegionByVacanciesFrontDTO> createListAllRegionsInfoByVacancies() {
        Set<String> allRegions = areaRepository.readDistinctByNameRegion();
        List<RegionByVacanciesFrontDTO> regionByVacanciesFrontDTOList = new ArrayList<>();
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
                createListSalaryForVacancies(area, listSalary);
            }
            if (!listSalary.isEmpty()) {
                medianValue = findMedianValueByListSalary(listSalary);
                fromSalary = Collections.min(listSalary);
                toSalary = Collections.max(listSalary);
            }
            regionByVacanciesFrontDTOList.add(new RegionByVacanciesFrontDTO(region, countCities, countVacancies, fromSalary, toSalary, medianValue));
        }
        log.info("regions: " + regionByVacanciesFrontDTOList.size());
        return regionByVacanciesFrontDTOList;
    }

    @Override
    public List<RegionByResumesFrontDTO> createListAllRegionsInfoByResumes() {
        Set<String> allRegions = areaRepository.readDistinctByNameRegion();
        List<RegionByResumesFrontDTO> regionByResumesFrontDTOList = new ArrayList<>();
        for (String region : allRegions) {
            Long medianValue = null;
            Long fromSalary = null;
            Long toSalary = null;
            Long countResumes = 0L;
            List<Area> areas = areaRepository.findAllByNameRegion(region);
            Long countCities = areas.stream().count();
            List<Long> listSalaryResumes = new ArrayList<>();
            for (Area area : areas) {
                countResumes += area.getResumes().stream().count();
                createListSalaryForResumes(area, listSalaryResumes);
            }
            if (!listSalaryResumes.isEmpty()) {
                medianValue = findMedianValueByListSalary(listSalaryResumes);
                fromSalary = Collections.min(listSalaryResumes);
                toSalary = Collections.max(listSalaryResumes);
            }
            regionByResumesFrontDTOList.add(new RegionByResumesFrontDTO(region, countCities, countResumes, fromSalary, toSalary, medianValue));
        }
        log.info("regions: " + regionByResumesFrontDTOList.size());
        return regionByResumesFrontDTOList;
    }

    @Override
    public List<CityByVacanciesFrontDTO> createListAllCitiesInfoByVacancies() {
        List<Area> areaList = (List<Area>) areaRepository.findAll();
        List<CityByVacanciesFrontDTO> citiesByVacanciesFrontDTO = new ArrayList<>();
        for (Area area : areaList) {
            Long medianValue = null;
            Long fromSalary = null;
            Long toSalary = null;
            String nameCity = area.getNameCity();
            String nameRegion = area.getNameRegion();
            Long countVacancies = area.getVacancyAreas().stream().count();
            List<Long> listSalaryVacancies = new ArrayList<>();
            createListSalaryForVacancies(area, listSalaryVacancies);
            if (!listSalaryVacancies.isEmpty()) {
                medianValue = findMedianValueByListSalary(listSalaryVacancies);
                fromSalary = Collections.min(listSalaryVacancies);
                toSalary = Collections.max(listSalaryVacancies);
            }
            citiesByVacanciesFrontDTO.add(new CityByVacanciesFrontDTO(nameCity, nameRegion, countVacancies, fromSalary, toSalary, medianValue));
        }
        log.info("cities " + citiesByVacanciesFrontDTO.size());
        return citiesByVacanciesFrontDTO;
    }

    @Override
    public List<CityByResumesFrontDTO> createListAllCitiesInfoByResumes() {
        List<Area> areaList = (List<Area>) areaRepository.findAll();
        List<CityByResumesFrontDTO> citiesByResumesFrontDTO = new ArrayList<>();
        for (Area area : areaList) {
            Long medianValue = null;
            Long fromSalary = null;
            Long toSalary = null;
            String nameCity = area.getNameCity();
            String nameRegion = area.getNameRegion();
            Long countResumes = area.getResumes().stream().count();
            List<Long> listSalaryResume = new ArrayList<>();
            createListSalaryForResumes(area, listSalaryResume);
            if (!listSalaryResume.isEmpty()) {
                medianValue = findMedianValueByListSalary(listSalaryResume);
                fromSalary = Collections.min(listSalaryResume);
                toSalary = Collections.max(listSalaryResume);
            }
            citiesByResumesFrontDTO.add(new CityByResumesFrontDTO(nameCity, nameRegion, countResumes, fromSalary, toSalary, medianValue));
        }
        log.info("cities " + citiesByResumesFrontDTO.size());
        return citiesByResumesFrontDTO;
    }

    private void createListSalaryForVacancies(Area area, List<Long> listSalary) {
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

    private void createListSalaryForResumes(Area area, List<Long> listSalary) {
        Set<Resume> resumeSet = area.getResumes();
        for (Resume resume : resumeSet) {
            if (resume.getSalaryValue() != null) {
                Long salaryRUB = convertCurrencyToRUR(resume.getSalaryCurrency(), resume.getSalaryValue());
                listSalary.add(salaryRUB);
            }
        }
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

    @Override
    public List<Area> createListCityWithRegion() {
        List<Area> listCityWithRegion = (List<Area>) areaRepository.findAll();
        log.info("cities " + listCityWithRegion.size());
        return listCityWithRegion;
    }
}
