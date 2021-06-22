package ru.ibs.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import ru.ibs.project.entities.EntityTest;
import ru.ibs.project.entities.Item;

public class RTService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String URL_VACANCIES = "https://api.hh.ru/vacancies?per_page=100&page=24&only_with_salary=true&order_by=publication_time&text=java Java програмист Java Developer";


    public void readVacancies() {

        RestTemplate restTemplate = new RestTemplate();
//        Map<String,Object> vacancy = restTemplate.getForObject(URL_VACANCIES, Map.class);
        //считал одну страницу, записал в БД
//        Integer found = (Integer) vacancy.get("found");
//        System.out.println(found + "найдено");

//        EntityTest entityTest = restTemplate.getForObject(URL_VACANCIES, EntityTest.class);
//        System.out.println(entityTest.getFound());
//        int count = 1;

//        if (entityTest.getErrors() == null)
//            System.out.println("OK");

//        for (Item item : entityTest.getItems()) { //для каждой вакансии(для каждого item)
//            System.out.print("id: " + item.getId() + " ");
//            System.out.print("Название вакансии: " + item.getName() + " ");
//            if (item.getArea() != null)   //проверяй все поля на != null
//                System.out.println("Город: " + item.getArea().getName());
//            if (item.getSalary() != null)
//                System.out.println("ЗП:" + item.getSalary().getFrom() + " - " + item.getSalary().getTo());
//            System.out.println(count++);
//            System.out.println();
//        }

    }
}
