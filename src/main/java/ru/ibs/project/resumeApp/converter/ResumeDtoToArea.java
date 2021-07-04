package ru.ibs.project.resumeApp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ibs.project.resumeApp.dto.hhDTO.respResumeDTOs.ResumeDTO;
import ru.ibs.project.entities.Area;

@Component
public class ResumeDtoToArea implements Converter<ResumeDTO, Area> {

    @Override
    public Area convert(ResumeDTO resumeDTO) {
        Area area = new Area();
        area.setNameCity(resumeDTO.getArea().getName());
        return area;
    }
}
