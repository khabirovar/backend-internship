package ru.ibs.project.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RsetTestController {

    @GetMapping("/")  //for server test
    public String readVacancies() {
        return "ok";
    }
}
