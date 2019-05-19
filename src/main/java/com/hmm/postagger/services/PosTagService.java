package com.hmm.postagger.services;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import com.hmm.postagger.utils.BigramModel;
import com.hmm.postagger.utils.SuffixTree;
import com.hmm.postagger.utils.SuffixTreeBuilder;
import com.hmm.postagger.utils.Viterbi;

public class PosTagService {

    private static Integer MAX_SUFFIX_LENGTH = 10;
    private static Integer MAX_WORD_FREQUENCY = 10;

    private BigramModel model = new BigramModel();
    private SuffixTree upperCaseTree;
    private SuffixTree lowerCaseTree;
    private Viterbi viterbi;

    public PosTagService() throws FileNotFoundException {
        System.out.println("Training HMM model...");
        model.train(getClass().getResourceAsStream("/static/WSJ_02-21.pos"));

        System.out.println("Using a maximum suffix length of " + MAX_SUFFIX_LENGTH);
        System.out.println("Using words with a maximum frequency of " + MAX_WORD_FREQUENCY + " to create suffix tree");

        SuffixTreeBuilder treeBuilder = new SuffixTreeBuilder(model, MAX_SUFFIX_LENGTH, MAX_WORD_FREQUENCY);
        upperCaseTree = treeBuilder.buildUpperCaseTree();
        lowerCaseTree = treeBuilder.buildLowerCaseTree();
        System.out.println("Finished training.");

        viterbi = new Viterbi(model, upperCaseTree, lowerCaseTree, MAX_SUFFIX_LENGTH);
    }

    public List<String> tagSentence(String sentence) {
        List<String> sentenceTokens = Arrays.asList(sentence.split(" "));
        List<String> tags = viterbi.run(sentenceTokens);

        return tags;
    }

}
