package ru.ibs.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ibs.project.entities.Resume;

@Repository
public interface ResumeRepository extends CrudRepository<Resume, Long> {
}
