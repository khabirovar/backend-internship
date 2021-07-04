package ru.ibs.project.resumeApp.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ibs.project.resumeApp.dto.hhDTO.respResumeDTOs.ResumeDTO;
import ru.ibs.project.resumeApp.services.interfaces.DataManipulationServiceResume;

import java.util.Set;

@Service
@Slf4j
public class DataManipulationServiceResumeImpl implements DataManipulationServiceResume {

    private final ConversionService conversionService;



    public DataManipulationServiceResumeImpl(
            @Qualifier("mvcConversionService") ConversionService conversionService
            ) {
        this.conversionService = conversionService;
    }




    @Override
    @Transactional
    public void createAll(Set<ResumeDTO> resumeDTOSet) {

    }

}
