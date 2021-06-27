package ru.ibs.project.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "area",
        uniqueConstraints =
                {@UniqueConstraint(columnNames = "name_area")}
)
public class Area extends EntityBase {

    @Column(name = "name_area")
    private String nameArea;

    @Column(name = "name_region")
    private String nameRegion;

//    @OneToMany(
//            mappedBy = "area",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true,
//            fetch = FetchType.LAZY
//    )
//    private List<Vacancy> vacancies;

    @OneToMany(mappedBy = "area",
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<VacancyArea> vacancyAreas;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "area", orphanRemoval = true)
//    private List<Vacancy> vacancies;
}
