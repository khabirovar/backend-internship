package ru.ibs.project.resumeApp.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ibs.project.entities.Area;
import ru.ibs.project.entities.Resume;
import ru.ibs.project.resumeApp.dto.hhDTO.respResumeDTOs.ContactDTO;
import ru.ibs.project.resumeApp.dto.hhDTO.respResumeDTOs.ResumeDTO;
import ru.ibs.project.services.interfaces.DataManipulationService;

import java.util.List;
import java.util.Objects;

@Component
public class ResumeDtoToResume implements Converter<ResumeDTO, Resume> {

    private DataManipulationService dataManipulationService;

    @Autowired
    public ResumeDtoToResume(DataManipulationService dataManipulationService) {
        this.dataManipulationService = dataManipulationService;
    }


    @Override
    public Resume convert(ResumeDTO resumeDTO) {
        Resume resume = new Resume();
        resume.setTitle(resumeDTO.getTitle());
        resume.setFirstName(resumeDTO.getFirst_name());
        resume.setLastName(resumeDTO.getLast_name());
        resume.setMiddleName(resumeDTO.getMiddle_name());
        resume.setAge(resumeDTO.getAge());
        resume.setSalaryCurrency(resumeDTO.getSalary().getCurrency());
        resume.setSalaryValue(resumeDTO.getSalary().getAmount());
        resume.setTotalExperienceMonths(resumeDTO.getTotal_experience().getMonths());
        resume.setArea(findAreaResumeByName(resumeDTO.getArea().getName()));
        List<ContactDTO> contactDTOList = resumeDTO.getContact();
        for (ContactDTO contactDTO : contactDTOList) {
            if (contactDTO.getType().getName().equals("Мобильный телефон"))
                resume.setPhoneNumber(contactDTO.getValue().getFormatted());
            if (contactDTO.getType().getName().equals("Эл. почта"))
                resume.setEmail(contactDTO.getValue().getFormatted());
        }
        return resume;
    }

    private Area findAreaResumeByName(String name) {
        return dataManipulationService.getAreaSet().
                stream().
                filter(data -> Objects.equals(data.getNameCity(),
                        name)).findFirst().get();
    }
}
