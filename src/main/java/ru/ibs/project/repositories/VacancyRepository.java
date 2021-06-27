package ru.ibs.project.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ibs.project.entities.Vacancy;

public interface VacancyRepository extends CrudRepository<Vacancy, Long> {
//    Vacancy getByNameVacancy(String nameVacancy);
}