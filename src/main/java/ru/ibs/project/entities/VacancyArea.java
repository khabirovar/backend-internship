package ru.ibs.project.entities;

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
    private Long fromSalary;

    @Column(name = "toSalary")
    private Long toSalary;

    @Column(name = "currencySalary")
    private String currencySalary;

    @Column(name = "nameEmployer")
    private String nameEmployer;

    @Column(name = "nameExperience")
    private String nameExperience;

    //    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE)
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    //    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE)
    @JoinColumn(name = "area_id")
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
