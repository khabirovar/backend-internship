package ru.ibs.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ibs.project.entities.VacancyArea;

@Repository
public interface VacancyAreaRepository extends CrudRepository<VacancyArea, Long> {
}
