package ru.ibs.project.vacancyApp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;
import ru.ibs.project.vacancyApp.entities.VacancyArea;

import java.util.concurrent.Callable;

@Slf4j
public class ConvertItemDTOCallableClass implements Callable<VacancyArea> {

    private ConversionService conversionService;
    private ItemDTO itemDTO;

    @Autowired
    public ConvertItemDTOCallableClass(
            @Qualifier("mvcConversionService") ConversionService conversionService,
            ItemDTO itemDTO
    ) {
        this.conversionService = conversionService;
        this.itemDTO = itemDTO;
    }


    @Override
    public VacancyArea call() throws InterruptedException {
        log.info("start thread " + Thread.currentThread().getId());
        VacancyArea vacancyArea = conversionService.convert(itemDTO, VacancyArea.class);
        log.info("finish thread " + Thread.currentThread().getId());
        return vacancyArea;
    }
}
