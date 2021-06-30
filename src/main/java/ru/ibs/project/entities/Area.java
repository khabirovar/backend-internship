package ru.ibs.project.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.opencsv.bean.CsvBindByName;
import lombok.*;
import org.hibernate.annotations.BatchSize;

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
                {@UniqueConstraint(columnNames = "name_city")}
)
public class Area extends EntityBase {

    @Column(name = "name_city")
    @CsvBindByName
    private String nameCity;  //!!исправить на nameCity

    @Column(name = "name_region")
    @CsvBindByName
    private String nameRegion;

    @OneToMany(mappedBy = "area",
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JsonBackReference
    @BatchSize(size = 100) //n+1 -> n/x+1
    private Set<VacancyArea> vacancyAreas;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Area area = (Area) o;
        return Objects.equals(nameCity, area.nameCity) &&
                Objects.equals(nameRegion, area.nameRegion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nameCity, nameRegion);
    }

    @Override
    public String toString() {
        return "Area{" +
                "nameCity='" + nameCity + '\'' +
                ", nameRegion='" + nameRegion + '\'' +
                ", id=" + id +
                '}';
    }
}
