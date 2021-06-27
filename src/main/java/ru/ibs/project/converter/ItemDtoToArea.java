package ru.ibs.project.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ibs.project.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;
import ru.ibs.project.entities.Area;


@Component
public class ItemDtoToArea implements Converter<ItemDTO, Area> {

    @Override
    public Area convert(ItemDTO itemDTO) {
        Area area = new Area();
        area.setNameArea(itemDTO.getArea().getName());
        return area;
    }
}
