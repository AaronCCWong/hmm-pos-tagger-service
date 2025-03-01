package com.hmm.postagger.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WakeUpController {

    @CrossOrigin
    @GetMapping("/wakeUp")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void wakeUp() {}

}
