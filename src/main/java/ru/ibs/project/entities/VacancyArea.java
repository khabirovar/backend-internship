package ru.ibs.project.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvRecurse;
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
}
