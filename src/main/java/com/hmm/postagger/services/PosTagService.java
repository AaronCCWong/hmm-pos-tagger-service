package com.hmm.postagger.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.hmm.postagger.utils.BigramModel;
import com.hmm.postagger.utils.SuffixTree;
import com.hmm.postagger.utils.SuffixTreeBuilder;
import com.hmm.postagger.utils.Viterbi;

public class PosTagService {

    private static Integer MAX_SUFFIX_LENGTH = 10;
    private static Integer MAX_WORD_FREQUENCY = 10;

    private BigramModel model;
    private SuffixTree upperCaseTree;
    private SuffixTree lowerCaseTree;
    private Viterbi viterbi;

    public PosTagService() throws FileNotFoundException {
        System.out.println("Training HMM model...");
        model = train(getClass().getResourceAsStream("/static/WSJ_02-21.pos"));

        System.out.println("Using a maximum suffix length of " + MAX_SUFFIX_LENGTH);
        System.out.println("Using words with a maximum frequency of " + MAX_WORD_FREQUENCY + " to create suffix tree");

        SuffixTreeBuilder treeBuilder = new SuffixTreeBuilder(MAX_SUFFIX_LENGTH, MAX_WORD_FREQUENCY);
        upperCaseTree = treeBuilder.buildUpperCaseTree(model);
        lowerCaseTree = treeBuilder.buildLowerCaseTree(model);
        System.out.println("Finished training.");

        viterbi = new Viterbi(model, upperCaseTree, lowerCaseTree, MAX_SUFFIX_LENGTH);
    }

    public List<String> tagSentence(String sentence) {
        List<String> sentenceTokens = Arrays.asList(sentence.split(" "));
        List<String> tags = viterbi.run(sentenceTokens);

        return tags;
    }

    private BigramModel train(InputStream file) throws FileNotFoundException {
        BigramModel bigramModel = new BigramModel();
        Scanner sc = new Scanner(file);
        String prevTag = "";

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isEmpty()) {
                prevTag = "";
                continue;
            }

            String[] wordTag = line.split("\t");
            String word = wordTag[0];
            String tag = wordTag[1];

            bigramModel.incrementWordCount(word);
            bigramModel.incrementTagCount(tag);
            bigramModel.incrementTagWordCount(tag, word);
            if (prevTag != null && !prevTag.isEmpty()) {
                bigramModel.incrementTagTansitionCount(prevTag, tag);
            } else if (prevTag.isEmpty()) {
                bigramModel.incrementTagStartCount(tag);
            }
            prevTag = tag;
        }

        sc.close();

        return bigramModel;
    }

}
