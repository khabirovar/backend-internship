package ru.ibs.project.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
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
}
