package ru.ibs.project.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvRecurse;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vacancyArea")
public class VacancyArea extends EntityBase {

    @Column(name = "fromSalary")
    @CsvBindByName
    private Long fromSalary;


    @Column(name = "toSalary")
    @CsvBindByName
    private Long toSalary;

    @Column(name = "currencySalary")
    @CsvBindByName
    private String currencySalary;

    @Column(name = "nameEmployer")
    @CsvBindByName
    private String nameEmployer;

    @Column(name = "nameExperience")
    @CsvBindByName
    private String nameExperience;


    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE)
    @JsonManagedReference
    @JoinColumn(name = "vacancy_id")
    @CsvRecurse
    private Vacancy vacancy;

//    @CsvBindByName(column = "location", required = true)
//    @CsvBindByPosition(position = 2)
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE)
    @JsonManagedReference  //
    @JoinColumn(name = "area_id")
    @CsvRecurse
    private Area area;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//        VacancyArea that = (VacancyArea) o;
//        return Objects.equals(fromSalary, that.fromSalary) &&
//                Objects.equals(toSalary, that.toSalary) &&
//                Objects.equals(currencySalary, that.currencySalary) &&
//                Objects.equals(nameEmployer, that.nameEmployer) &&
//                Objects.equals(nameExperience, that.nameExperience);
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(super.hashCode(), fromSalary, toSalary, currencySalary, nameEmployer, nameExperience);
//    }
//
//
//    @Override
//    public String toString() {
//        return "VacancyArea{" +
//                "fromSalary=" + fromSalary +
//                ", toSalary=" + toSalary +
//                ", currencySalary='" + currencySalary + '\'' +
//                ", nameEmployer='" + nameEmployer + '\'' +
//                ", nameExperience='" + nameExperience + '\'' +
//                ", id=" + id +
//                '}';
//    }
}
