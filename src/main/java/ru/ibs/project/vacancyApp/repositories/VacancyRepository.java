package ru.ibs.project.vacancyApp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ibs.project.vacancyApp.entities.Vacancy;

@Repository
public interface VacancyRepository extends CrudRepository<Vacancy, Long> {
}