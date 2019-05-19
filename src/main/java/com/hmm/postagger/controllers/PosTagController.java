package com.hmm.postagger.controllers;

import java.io.FileNotFoundException;
import java.util.List;

import com.hmm.postagger.services.PosTagService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PosTagController {

    private final PosTagService posTagService = new PosTagService();

    public PosTagController() throws FileNotFoundException {}

    @CrossOrigin(origins = "https://www.aaronccwong.com")
    @GetMapping("/tagSentence")
    public List<String> tagSentence(@RequestParam(value = "sentence") String sentence) {
        return posTagService.tagSentence(sentence);
    }

}
