package ru.ibs.project.services.interfaces;

import ru.ibs.project.dto.frontDTO.DownloadRequestDTO;
import ru.ibs.project.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;

import java.util.Set;

public interface DownloadFromHHService {

    Set<ItemDTO> downloadAllItemDTOsByDownloadRequestDTO(DownloadRequestDTO requestDTO);

    String downloadExperienceByItemDTOid(Long id);

    String downloadNameRegionByNameCity(String nameArea);
}

