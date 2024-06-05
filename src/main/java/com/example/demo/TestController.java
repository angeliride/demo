package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    //adres - usługa, która działa na get
    @GetMapping("hello")
    public String test() {
        return "Hello world!";
    }
}
