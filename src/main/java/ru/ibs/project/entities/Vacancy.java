package ru.ibs.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity

@JsonIgnoreProperties (ignoreUnknown = true) //любые поля, не связанные с полями класса, должны быть проигнорированы.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {

    private Long id;
    private String name;
    private String area;
//    private String items;
}
