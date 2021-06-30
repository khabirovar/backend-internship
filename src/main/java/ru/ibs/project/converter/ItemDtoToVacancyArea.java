package ru.ibs.project.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ibs.project.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;
import ru.ibs.project.entities.Area;
import ru.ibs.project.entities.Vacancy;
import ru.ibs.project.entities.VacancyArea;
import ru.ibs.project.services.interfaces.DownloadFromHHService;
import ru.ibs.project.services.interfaces.DataManipulationService;

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
        vacancyArea.setFromSalary(itemDTO.getSalary().getFrom());
        vacancyArea.setToSalary(itemDTO.getSalary().getTo());
        vacancyArea.setCurrencySalary(itemDTO.getSalary().getCurrency());
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
