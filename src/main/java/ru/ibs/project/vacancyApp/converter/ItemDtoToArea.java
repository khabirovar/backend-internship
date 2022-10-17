package ru.ibs.project.vacancyApp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ibs.project.entities.Area;
import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;


@Component
public class ItemDtoToArea implements Converter<ItemDTO, Area> {

    @Override
    public Area convert(ItemDTO itemDTO) {
        Area area = new Area();
        area.setNameCity(itemDTO.getArea().getName());
        return area;
    }
}
