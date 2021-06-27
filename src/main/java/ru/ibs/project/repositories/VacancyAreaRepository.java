package ru.ibs.project.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ibs.project.entities.VacancyArea;

public interface VacancyAreaRepository extends CrudRepository<VacancyArea, Long> {
}
