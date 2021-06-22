package ru.ibs.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ibs.project.services.RTService;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

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
//        String vacancy = restTemplate.getForObject("https://api.hh.ru/vacancies", String.class);
//        System.out.println(vacancy);

////        HttpEntity request = new HttpEntity(headers);
////            restTemplate.getForObject("http://localhost:8080/vacancies", String.class);
////       final ResponseEntity<String> getEntity = restTemplate.getForEntity(uri, String.class);
////       String aa =  getEntity.getBody();
        RTService rtService = new RTService();
        rtService.readVacancies();
    }
}
