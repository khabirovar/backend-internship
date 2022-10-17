package ru.ibs.project.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.opencsv.bean.CsvBindByName;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vacancy",
        uniqueConstraints =
                {@UniqueConstraint(columnNames = "name_vacancy")}
)
public class Vacancy extends EntityBase {

    @Column(name = "name_vacancy")
    @CsvBindByName
    private String nameVacancy;

    @OneToMany(mappedBy = "vacancy",
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
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(nameVacancy, vacancy.nameVacancy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nameVacancy);
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "nameVacancy='" + nameVacancy + '\'' +
                ", id=" + id +
                '}';
    }
}
