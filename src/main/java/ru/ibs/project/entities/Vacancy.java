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
@Table(name = "vacancy",
        uniqueConstraints =
                { @UniqueConstraint(columnNames = "name_vacancy") }
)
public class Vacancy extends EntityBase {

    @Column(name = "name_vacancy")
    private String nameVacancy;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "experience_id")
//    private Experience experience;//many to one

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "area_id")
//    private Area area; //many to one


//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "employer_id")
//    private Employer employer;  //many to one. тут наименовании организации. может быть null?

    //    @OneToOne(
//            mappedBy = "vacancy",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true,
//            fetch = FetchType.LAZY
//    )
//    @OneToOne(cascade = CascadeType.ALL)
//    private Salary salary; //one to one.

    @OneToMany(mappedBy = "vacancy",
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<VacancyArea> vacancyAreas;
//    @BatchSize(size = 200) n+1 -> n/x+1
//    vacancy.getVacancyAreas.forEach(va->{va.getSalary})

//    @OneToMany(mappedBy = "vacancy")
//    private Set<VacancySalary> vacancySalaries;


//    @ManyToOne(cascade = CascadeType.ALL)  //manyToMany
//    @JoinColumn( name = "AREA_ID", referencedColumnName = "ID")
//    private Area area;

}
