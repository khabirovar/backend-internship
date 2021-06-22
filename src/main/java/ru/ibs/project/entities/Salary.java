package ru.ibs.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true) //любые поля, не связанные с полями класса, должны быть проигнорированы.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salary { //это сущьность для мапинга из стороннего API
    private Integer from;
    private Integer to;
    private String currency;
}
