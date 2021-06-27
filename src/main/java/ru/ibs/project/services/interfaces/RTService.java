package ru.ibs.project.services.interfaces;

import ru.ibs.project.dto.frontDTO.DownloadRequestDTO;
import ru.ibs.project.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;

import java.util.Set;

public interface RTService {

    Set<ItemDTO> readAllFromHH(DownloadRequestDTO requestDTO);

    String readExperience(Long id);

    String readRegion(String nameArea);
}

