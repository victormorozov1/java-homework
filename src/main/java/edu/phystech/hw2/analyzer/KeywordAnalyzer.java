package edu.phystech.hw2.analyzer;

import java.util.Arrays;
import java.util.List;

public abstract class KeywordAnalyzer implements TextAnalyzer {

    private final List<String> keywords;
    private final Label label;

    public KeywordAnalyzer(List<String> keywords, Label label) {
        this.keywords = keywords;
        this.label = label;
    }

    @Override
    public Label processText(String text) {
        var a = Arrays.asList(text.split(" "));
        for (String keyword : keywords) {
            if (a.contains(keyword)) {
                return label;
            }
        }
        return Label.OK;
    }
}
