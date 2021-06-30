package ru.ibs.project.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.ibs.project.entities.VacancyArea;

import java.util.List;

public interface VacancyAreaRepository extends CrudRepository<VacancyArea, Long> {

//    @Query(value = "select * from vacancy_area", nativeQuery = true)
//    List<VacancyArea> myMeth();

}
