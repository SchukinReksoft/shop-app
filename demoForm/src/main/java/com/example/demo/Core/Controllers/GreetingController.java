package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class GreetingController {

    @GetMapping(value = "/greeting", produces = "application/json")
    public Integer greetingSubmit(@RequestParam(name="num1") Integer number1, @RequestParam(name="num2") Integer num2) {
        return number1 + num2;
    }

}