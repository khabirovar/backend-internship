package ru.ibs.project.vacancyApp.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.ibs.project.exceptions.MyException;
import ru.ibs.project.vacancyApp.dto.frontDTO.DownloadRequestDTO;
import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;
import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.MainResponseVacanciesHHDTO;
import ru.ibs.project.vacancyApp.dto.hhDTO.respDictionaryDTOs.RegionDTO;
import ru.ibs.project.vacancyApp.dto.hhDTO.respDictionaryDTOs.ResponseCountryHHDTO;
import ru.ibs.project.vacancyApp.dto.hhDTO.respVacancyDTOs.ResponseVacancyHHDTO;
import ru.ibs.project.vacancyApp.services.interfaces.DownloadFromHHService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class DownloadFromHHServiceVacancyImpl implements DownloadFromHHService {

    private RestTemplate restTemplate;

    private String URL_VACANCIES;

    private String URL_DICTIONARY;

    @Autowired
    public DownloadFromHHServiceVacancyImpl(
            RestTemplate restTemplate,
            @Value("${const.urlVacancies}") String URL_VACANCIES,
            @Value("${const.urlDictionary}") String URL_DICTIONARY) {
        this.restTemplate = restTemplate;
        this.URL_VACANCIES = URL_VACANCIES;
        this.URL_DICTIONARY = URL_DICTIONARY;
    }


    @Override
    public Set<ItemDTO> downloadAllItemDTOsByDownloadRequestDTO(DownloadRequestDTO downloadRequestDTO) {
        String url = URL_VACANCIES;
        Set<ItemDTO> vacanciesDTO = new LinkedHashSet<>();
        if (!StringUtils.isEmpty(downloadRequestDTO.getName()))
            url += "?text=" + downloadRequestDTO.getName() + "&per_page=100&search_field=name";
        if (!StringUtils.isEmpty(downloadRequestDTO.getOrderBy()))
            url += "&order_by=publication_time";

        if (downloadRequestDTO.getOnlyWithSalary())
            url += "&only_with_salary=true";

        MainResponseVacanciesHHDTO mainResponseVacanciesHHDTO = restTemplate.getForObject(url, MainResponseVacanciesHHDTO.class);
        log.info("found " + mainResponseVacanciesHHDTO.getFound() + " vacancies");
            for (int page = 0; page < mainResponseVacanciesHHDTO.getPages(); page++) {
                String conUrl = url + "&page=" + page;
                MainResponseVacanciesHHDTO responseVacanciesPage =
                        restTemplate.getForObject(conUrl, MainResponseVacanciesHHDTO.class);
                vacanciesDTO.addAll(responseVacanciesPage.getItems());
                log.info("get data from: " + conUrl);
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
    public List<RegionDTO> downloadRegionDTOList() {
        String countryUrlDictionary = URL_DICTIONARY;
        ResponseCountryHHDTO responseCountryHHDTO = restTemplate.getForObject(countryUrlDictionary,
                ResponseCountryHHDTO.class);
        return responseCountryHHDTO.getAreas();
    }
}
