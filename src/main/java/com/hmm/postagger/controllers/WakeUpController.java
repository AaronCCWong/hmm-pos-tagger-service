package com.hmm.postagger.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WakeUpController {

    @GetMapping("/wakeUp")
    public void wakeUp() {}

}
