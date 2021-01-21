package com.example.demo.api.rest;

import com.example.demo.core.models.Greeting;
import com.example.demo.core.services.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class GreetingController {

    private final CalculatorService calculatorService;

    @Autowired
    public GreetingController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping(value = "/greeting", produces = "application/json")
    public Integer greetingSubmit(@RequestParam(name="num1") Integer number1, @RequestParam(name="num2") Integer num2) {
        return calculatorService.add(number1, num2);
    }

    @PostMapping(value = "/greeting", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public Greeting greetingSubmit(@RequestBody Greeting greeting) {
        Greeting result = new Greeting();
        result.setNum1(greeting.getNum1());
        result.setNum2(greeting.getNum2());
        result.setResult(calculatorService.add(greeting.getNum1(), greeting.getNum2()));
        return result;
    }
}