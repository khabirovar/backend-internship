package ru.ibs.project.dto.frontDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DownloadRequestDTO {

    private String name;

    private Boolean onlyWithSalary;

    private String orderBy; //enum???  сортировать по:

    //publication_time
    //salary_desc - по убыванию дохода
    //salary_asc - по возрастанию дохода
    //relevance - по соответствию


}
