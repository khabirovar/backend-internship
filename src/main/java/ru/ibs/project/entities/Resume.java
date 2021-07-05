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
@Table(name = "resume")
public class Resume extends EntityBase {

    @Column(name = "title")
    @CsvBindByName
    private String title;

    @Column(name = "first_name")
    @CsvBindByName
    private String firstName;

    @Column(name = "last_name")
    @CsvBindByName
    private String lastName;

    @Column(name = "middle_name")
    @CsvBindByName
    private String middleName;

    @Column(name = "age")
    @CsvBindByName
    private Long age;

    @Column(name = "phone_number")
    @CsvBindByName
    private String phoneNumber;

    @Column(name = "email")
    @CsvBindByName
    private String email;

    @Column(name = "salary_value")
    @CsvBindByName
    private Long salaryValue;


    @Column(name = "salary_currency")
    @CsvBindByName
    private String salaryCurrency;

    @Column(name = "total_experience_months")
    @CsvBindByName
    private Long totalExperienceMonths;


    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE)
    @JsonManagedReference
    @JoinColumn(name = "area_id")
    @CsvRecurse
    private Area area;
}
