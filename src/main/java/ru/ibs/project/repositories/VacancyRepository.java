package ru.ibs.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ibs.project.entities.Vacancy;

@Repository
public interface VacancyRepository extends CrudRepository<Vacancy, Long> {
}