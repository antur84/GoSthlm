package com.filreas.shared.dto;

import java.io.Serializable;

public class DeviationDto implements Serializable{
    private String consequence;
    private int importanceLevel;
    private String text;

    public void setConsequence(String consequence) {
        this.consequence = consequence;
    }

    public void setImportanceLevel(int importanceLevel) {
        this.importanceLevel = importanceLevel;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getConsequence() {
        return consequence;
    }

    public int getImportanceLevel() {
        return importanceLevel;
    }

    public String getText() {
        return text;
    }
}
