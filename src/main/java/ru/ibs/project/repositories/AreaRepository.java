package ru.ibs.project.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ibs.project.entities.Area;

import java.util.List;
import java.util.Set;
@Repository
public interface AreaRepository extends CrudRepository<Area, Long> {

    @Query(value = "SELECT DISTINCT name_region FROM area", nativeQuery = true)
    Set<String> readDistinctByNameRegion();

    List<Area> findAllByNameRegion(String nameRegion);
}
