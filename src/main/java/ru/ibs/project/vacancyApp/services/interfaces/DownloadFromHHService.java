package ru.ibs.project.vacancyApp.services.interfaces;

import ru.ibs.project.vacancyApp.dto.frontDTO.DownloadRequestDTO;
import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;
import ru.ibs.project.vacancyApp.dto.hhDTO.respDictionaryDTOs.RegionDTO;

import java.util.List;
import java.util.Set;

public interface DownloadFromHHService {

    Set<ItemDTO> downloadAllItemDTOsByDownloadRequestDTO(DownloadRequestDTO requestDTO);

    String downloadExperienceByItemDTOid(Long id);

    List<RegionDTO> downloadRegionDTOList();
}

