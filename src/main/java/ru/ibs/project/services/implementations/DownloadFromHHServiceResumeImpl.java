package ru.ibs.project.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.ibs.project.exceptions.MyException;
import ru.ibs.project.resumeApp.dto.frontDTO.DownloadRequestDTOResume;
import ru.ibs.project.resumeApp.dto.hhDTO.respAllResumesDTOs.ItemDTOResume;
import ru.ibs.project.resumeApp.dto.hhDTO.respAllResumesDTOs.MainResponseResumesHHDTO;
import ru.ibs.project.resumeApp.dto.hhDTO.respResumeDTOs.ResumeDTO;
import ru.ibs.project.services.interfaces.DownloadFromHHServiceResume;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DownloadFromHHServiceResumeImpl implements DownloadFromHHServiceResume {

    private String URL_RESUMES;

    private RestTemplate restTemplate;


    @Autowired
    public DownloadFromHHServiceResumeImpl(
            @Value("${const.urlResumes}") String URL_RESUMES,
            RestTemplate restTemplate) {
        this.URL_RESUMES = URL_RESUMES;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<String> downloadAllIdDTOsByDownloadRequestDTO(DownloadRequestDTOResume requestDTOResume) {
        String url = URL_RESUMES;
        List<String> listIdResume;
        Set<ItemDTOResume> resumesDTO = new LinkedHashSet<>();

        if (!StringUtils.isEmpty(requestDTOResume.getName()))
            url += "?text=" + requestDTOResume.getName() + "&per_page=100";
        else
            throw new MyException("Resume name required");

        MainResponseResumesHHDTO mainResponseResumesHHDTO = restTemplate.getForObject(url, MainResponseResumesHHDTO.class);

        log.info("found " + mainResponseResumesHHDTO.getFound() + " resumes");
        if (mainResponseResumesHHDTO.getFound() == 0)
            throw new MyException("this resume doesn't exist");
        else {
            for (int page = 0; page < mainResponseResumesHHDTO.getPages(); page++) {
                String conUrl = url + "&page=" + page;
                MainResponseResumesHHDTO responseResumesPage =
                        restTemplate.getForObject(conUrl, MainResponseResumesHHDTO.class);
                resumesDTO.addAll(responseResumesPage.getItems());
                log.info("get data from: " + conUrl);
            }
        }
        listIdResume = resumesDTO
                .stream()
                .map(ItemDTOResume::getId)
                .collect(Collectors.toList());
        return listIdResume;
    }

    public Set<ResumeDTO> downloadAllResumeById(List<String> listIdResume) {
        Set<ResumeDTO> resumeDTOSet = new LinkedHashSet<>();
        for (String idResume : listIdResume) {
            String url = URL_RESUMES + '/' + idResume;
            ResumeDTO resumeDTO = restTemplate.getForObject(url, ResumeDTO.class);
            resumeDTOSet.add(resumeDTO);
        }
        return resumeDTOSet;
    }
}
