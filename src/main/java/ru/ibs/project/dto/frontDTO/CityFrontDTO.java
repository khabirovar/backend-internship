package ru.ibs.project.dto.frontDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityFrontDTO {
    private String nameCity; //город
    private String nameRegion; //область
    private Long countVacancies; //количество вакансий в этом городе
    private Long minSalary;
    private Long maxSalary;
    private Long medianValue;
    //медиана


    //тут указать количество вакансий в этом регионе,
    //самая нижняя и самая верхняя граница по региону
    //медианное значение з/п

}
