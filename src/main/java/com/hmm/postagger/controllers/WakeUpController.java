package com.hmm.postagger.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WakeUpController {

    @RequestMapping("/wakeUp")
    public void wakeUp() {}

}
