package ru.ibs.project.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {

    private Long id;
    private String name;
    private String area;
}
