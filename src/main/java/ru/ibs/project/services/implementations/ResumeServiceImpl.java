package ru.ibs.project.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ibs.project.entities.Resume;
import ru.ibs.project.entities.VacancyArea;
import ru.ibs.project.repositories.ResumeRepository;
import ru.ibs.project.services.interfaces.ResumeService;

import java.util.List;

@Service
@Slf4j
public class ResumeServiceImpl implements ResumeService {

    private ResumeRepository resumeRepository;

    @Autowired
    public ResumeServiceImpl(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    @Override
    public List<Resume> createAllResumeList() {
        List<Resume> allResumes = (List<Resume>) resumeRepository.findAll();
        log.info("size resumes:" + allResumes.size());
        return allResumes;
    }
}
