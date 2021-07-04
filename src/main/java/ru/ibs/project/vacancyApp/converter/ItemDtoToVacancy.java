package ru.ibs.project.vacancyApp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;
import ru.ibs.project.entities.Vacancy;

@Component
public class ItemDtoToVacancy implements Converter<ItemDTO, Vacancy> {

    @Override
    public Vacancy convert(ItemDTO itemDTO) {
        Vacancy vacancy = new Vacancy();
        vacancy.setNameVacancy(itemDTO.getName());
        return vacancy;
    }
}
