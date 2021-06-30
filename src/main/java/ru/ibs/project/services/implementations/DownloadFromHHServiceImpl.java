package ru.ibs.project.services.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.ibs.project.dto.frontDTO.DownloadRequestDTO;
import ru.ibs.project.dto.hhDTO.respAllVacanciesDTOs.AreaDTO;
import ru.ibs.project.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;
import ru.ibs.project.dto.hhDTO.respAllVacanciesDTOs.ResponseVacanciesHHDTO;
import ru.ibs.project.dto.hhDTO.respDictionaryDTOs.RegionDTO;
import ru.ibs.project.dto.hhDTO.respDictionaryDTOs.ResponseCountryDictionaryHHDTO;
import ru.ibs.project.dto.hhDTO.respDictionaryDTOs.ResponseCountryHHDTO;
import ru.ibs.project.dto.hhDTO.respVacancyDTOs.ResponseVacancyHHDTO;
import ru.ibs.project.exceptions.MyException;
import ru.ibs.project.services.interfaces.DownloadFromHHService;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class DownloadFromHHServiceImpl implements DownloadFromHHService {

    private RestTemplate restTemplate;

    private ObjectMapper objectMapper;

    private String URL_VACANCIES;

    private String URL_DICTIONARY;

    public DownloadFromHHServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper,
                                     @Value("${const.urlVacancies}") String URL_VACANCIES,
                                     @Value("${const.urlDictionary}") String URL_DICTIONARY) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.URL_VACANCIES = URL_VACANCIES;
        this.URL_DICTIONARY = URL_DICTIONARY;
    }


    public Set<ItemDTO> downloadAllItemDTOsByDownloadRequestDTO(DownloadRequestDTO downloadRequestDTO) {
        String url = URL_VACANCIES;
        Set<ItemDTO> vacanciesDTO = new LinkedHashSet<>();

        if (!StringUtils.isEmpty(downloadRequestDTO.getName()))
            url += "?text=" + downloadRequestDTO.getName() + "&per_page=100";
        else
            throw new MyException("Job name required");

        if (!StringUtils.isEmpty(downloadRequestDTO.getOrderBy()))
            url += "&order_by=publication_time";

        if (downloadRequestDTO.getOnlyWithSalary())
            url += "&only_with_salary=true";

        ResponseVacanciesHHDTO responseVacanciesHHDTO = restTemplate.getForObject(url, ResponseVacanciesHHDTO.class);
        if (responseVacanciesHHDTO.getFound() == 0)
            throw new MyException("this vacancy doesn't exist");
        else {
            for (int page = 0; page < responseVacanciesHHDTO.getPages(); page++) {
                String conUrl = url + "&page=" + page;
                ResponseVacanciesHHDTO responseVacanciesPage =
                        restTemplate.getForObject(conUrl, ResponseVacanciesHHDTO.class);
                vacanciesDTO.addAll(responseVacanciesPage.getItems());
                log.info("get data from: " + conUrl);
            }
        }
        return vacanciesDTO;
    }

    @Override
    public String downloadExperienceByItemDTOid(Long id) {
        String url = URL_VACANCIES + '/' + id.toString();
        ResponseVacancyHHDTO responseVacancyHHDTO = restTemplate.getForObject(url, ResponseVacancyHHDTO.class);
        return responseVacancyHHDTO.getExperience().getName();
    }

    @Override
    public String downloadNameRegionByNameCity(String nameArea) {
        if (nameArea.equals("Санкт-Петербург") || nameArea.equals("Москва"))
            return nameArea;
        List<Long> countryIds = new ArrayList<>();
        String jsonAreaArray = restTemplate.getForObject(URL_DICTIONARY, String.class);
        try {
            List<ResponseCountryDictionaryHHDTO> listResponseCountryDictionaryHHDTO =
                    objectMapper.readValue(jsonAreaArray,
                            new TypeReference<List<ResponseCountryDictionaryHHDTO>>() {
                            });
            listResponseCountryDictionaryHHDTO.forEach(responseCountryDictionaryHHDTO -> {
                countryIds.add(responseCountryDictionaryHHDTO.getId());
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String nameRegion = "Вне РФ";
        for (Long countryId : countryIds) {
            String countryUrlDictionary = URL_DICTIONARY + countryId;
            ResponseCountryHHDTO responseCountryHHDTO = restTemplate.getForObject(countryUrlDictionary,
                    ResponseCountryHHDTO.class);
            List<RegionDTO> regionDTOS = responseCountryHHDTO.getAreas();  //области
            for (RegionDTO regionDTO : regionDTOS) {
                List<AreaDTO> cities = regionDTO.getAreas();
                for (AreaDTO city : cities) {
                    if (city.getName().equals(nameArea))
                        return regionDTO.getName();
                }
            }
        }
        return nameRegion;
    }
}
