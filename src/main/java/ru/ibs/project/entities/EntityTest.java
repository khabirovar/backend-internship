package ru.ibs.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) //любые поля, не связанные с полями класса, должны быть проигнорированы.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityTest {  //это сущьность для мапинга из стороннего API
    Integer found;
    List<Item> items;
    List<Error> errors;
}
