package ru.ibs.project.vacancyApp.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ibs.project.vacancyApp.entities.Area;
import ru.ibs.project.vacancyApp.entities.Vacancy;
import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;
import ru.ibs.project.vacancyApp.entities.VacancyArea;
import ru.ibs.project.vacancyApp.services.interfaces.DataManipulationService;
import ru.ibs.project.vacancyApp.services.interfaces.DownloadFromHHService;

import java.util.Objects;

@Component
public class ItemDtoToVacancyArea implements Converter<ItemDTO, VacancyArea> {

    private DownloadFromHHService downloadFromHHService;

    private DataManipulationService dataManipulationService;

    @Autowired
    public ItemDtoToVacancyArea(DownloadFromHHService downloadFromHHService, DataManipulationService dataManipulationService) {
        this.downloadFromHHService = downloadFromHHService;
        this.dataManipulationService = dataManipulationService;
    }

    @Override
    public VacancyArea convert(ItemDTO itemDTO) {
        VacancyArea vacancyArea = new VacancyArea();
        if (itemDTO.getSalary() != null) {
            vacancyArea.setFromSalary(itemDTO.getSalary().getFrom());
            vacancyArea.setToSalary(itemDTO.getSalary().getTo());
            vacancyArea.setCurrencySalary(itemDTO.getSalary().getCurrency());
        }
        vacancyArea.setNameEmployer(itemDTO.getEmployer().getName());
        vacancyArea.setNameExperience(downloadFromHHService.downloadExperienceByItemDTOid(itemDTO.getId()));
        vacancyArea.setArea(findAreaByName(itemDTO.getArea().getName()));
        vacancyArea.setVacancy(findVacancyByName(itemDTO.getName()));
        return vacancyArea;
    }

    private Vacancy findVacancyByName(String name) {
        return dataManipulationService.getVacancySet().
                stream().
                filter(data -> Objects.equals(data.getNameVacancy(),
                        name)).findFirst().get();
    }

    private Area findAreaByName(String name) {
        return dataManipulationService.getAreaSet().
                stream().
                filter(data -> Objects.equals(data.getNameCity(),
                        name)).findFirst().get();
    }
}
