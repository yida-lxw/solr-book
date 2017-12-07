package com.yida.solr.book.examples.ch16.multilanguage;

public class DetectedLanguage {
    private final String langCode;
    private final Double certainty;

    public DetectedLanguage(String lang, Double certainty) {
        this.langCode = lang;
        this.certainty = certainty;
    }

    public String getLangCode() {
        return this.langCode;
    }

    public Double getCertainty() {
        return this.certainty;
    }
}