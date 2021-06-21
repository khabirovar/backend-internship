package ru.ibs.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.ibs.project.entities.Vacancy;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

@SpringBootApplication
public class Main {

//    @Autowired
//    private static RestTemplate restTemplate;

    public static void main(String[] args) throws MalformedURLException, URISyntaxException {
        SpringApplication.run(Main.class);
//        URI uri = new URI("http://localhost:8080/vacancies");
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.APPLICATION_JSON);
//        RestTemplate restTemplate = new RestTemplate();
//        Vacancy vacancy = restTemplate.getForObject("https://api.hh.ru/vacancies", Vacancy.class);
//        System.out.println(vacancy);
//
////        HttpEntity request = new HttpEntity(headers);
////            restTemplate.getForObject("http://localhost:8080/vacancies", String.class);
////       final ResponseEntity<String> getEntity = restTemplate.getForEntity(uri, String.class);
////       String aa =  getEntity.getBody();
//        System.out.println(aa);




    }
}
