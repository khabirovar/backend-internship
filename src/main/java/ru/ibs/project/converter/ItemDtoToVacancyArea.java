package ru.ibs.project.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ibs.project.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;
import ru.ibs.project.entities.Area;
import ru.ibs.project.entities.Vacancy;
import ru.ibs.project.entities.VacancyArea;
import ru.ibs.project.services.interfaces.DownloadFromHHService;
import ru.ibs.project.services.interfaces.VacancyService;

import java.util.Objects;

@Component
public class ItemDtoToVacancyArea implements Converter<ItemDTO, VacancyArea> {

    private DownloadFromHHService downloadFromHHService;

    private VacancyService vacancyService;

    @Autowired
    public ItemDtoToVacancyArea(DownloadFromHHService downloadFromHHService, VacancyService vacancyService) {
        this.downloadFromHHService = downloadFromHHService;
        this.vacancyService = vacancyService;
    }

    @Override
    public VacancyArea convert(ItemDTO itemDTO) {
        VacancyArea vacancyArea = new VacancyArea();
        vacancyArea.setFromSalary(itemDTO.getSalary().getFrom());
        vacancyArea.setToSalary(itemDTO.getSalary().getTo());
        vacancyArea.setCurrencySalary(itemDTO.getSalary().getCurrency());
        vacancyArea.setNameEmployer(itemDTO.getEmployer().getName());
        vacancyArea.setNameExperience(downloadFromHHService.downloadExperienceByItemDTOid(itemDTO.getId()));
//
//        Set<Area> areaSet = vacancyService.getAreaSet();
//        Set<Vacancy> vacancySet = vacancyService.getVacancySet();
//
//        Area areaByName = areaSet.
//                stream().
//                filter(data -> Objects.equals(data.getNameArea(),
//                        itemDTO.getArea().getName())).findFirst().get();
//                  Area areaFromDB = areaRepository.getByNameArea(itemDTO.getArea().getName());//посмотреть, есть ли в БД такой area
//        vacancyArea.setArea(areaByName);
//        Vacancy vacancyByName = vacancySet.
//                stream().
//                filter(data -> Objects.equals(data.getNameVacancy(),
//                        itemDTO.getName())).findFirst().get();
//              Vacancy vacancyFromDB = vacancyRepository.getByNameVacancy(itemDTO.getName());
//        vacancyArea.setVacancy(vacancyByName);
        vacancyArea.setArea(findAreaByName(itemDTO.getArea().getName()));
        vacancyArea.setVacancy(findVacancyByName(itemDTO.getName()));
        return vacancyArea;
    }

    private Vacancy findVacancyByName(String name) {
        return vacancyService.getVacancySet().
                stream().
                filter(data -> Objects.equals(data.getNameVacancy(),
                        name)).findFirst().get();
    }

    private Area findAreaByName(String name) {
        return vacancyService.getAreaSet().
                stream().
                filter(data -> Objects.equals(data.getNameCity(),
                        name)).findFirst().get();
    }
}
