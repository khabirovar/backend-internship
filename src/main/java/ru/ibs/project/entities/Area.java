package ru.ibs.project.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "area",
        uniqueConstraints =
                {@UniqueConstraint(columnNames = "name_area")}
)
public class Area extends EntityBase {

    @Column(name = "name_area")
    private String nameArea;  //!!исправить на nameCity

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Area area = (Area) o;
        return Objects.equals(nameArea, area.nameArea) &&
                Objects.equals(nameRegion, area.nameRegion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nameArea, nameRegion);
    }

    @Override
    public String toString() {
        return "Area{" +
                "nameArea='" + nameArea + '\'' +
                ", nameRegion='" + nameRegion + '\'' +
                ", id=" + id +
                '}';
    }
}
