/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.bean.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Julien, Derek Herbert
 */

public class Survey implements Serializable {

    private static final long serialVersionUID = 1L;
    private int surveyId;
    private String question;
    private String choice1;
    private int votes1;
    private String choice2;
    private int votes2;
    private String choice3;
    private int votes3;
    private String choice4;
    private int votes4;
    private String choice5;
    private int votes5;
    //0 means not in effect
    //1 means in effect
    private int isInEffect;

    public Survey() {
    }    

    public Survey(int surveyId, String question, String choice1, int votes1, String choice2, int votes2, String choice3, int votes3, String choice4, int votes4, String choice5, int votes5) {
        this.surveyId = surveyId;
        this.question = question;
        this.choice1 = choice1;
        this.votes1 = votes1;
        this.choice2 = choice2;
        this.votes2 = votes2;
        this.choice3 = choice3;
        this.votes3 = votes3;
        this.choice4 = choice4;
        this.votes4 = votes4;
        this.choice5 = choice5;
        this.votes5 = votes5;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public int getVotes1() {
        return votes1;
    }

    public void setVotes1(int votes1) {
        this.votes1 = votes1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public int getVotes2() {
        return votes2;
    }

    public void setVotes2(int votes2) {
        this.votes2 = votes2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public int getVotes3() {
        return votes3;
    }

    public void setVotes3(int votes3) {
        this.votes3 = votes3;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public int getVotes4() {
        return votes4;
    }

    public void setVotes4(int votes4) {
        this.votes4 = votes4;
    }

    public String getChoice5() {
        return choice5;
    }

    public void setChoice5(String choice5) {
        this.choice5 = choice5;
    }

    public int getVotes5() {
        return votes5;
    }

    public void setVotes5(int votes5) {
        this.votes5 = votes5;
    }

    public int getIsInEffect() {
        return isInEffect;
    }

    public void setIsInEffect(int isInEffect) {
        this.isInEffect = isInEffect;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.surveyId;
        hash = 97 * hash + Objects.hashCode(this.question);
        hash = 97 * hash + Objects.hashCode(this.choice1);
        hash = 97 * hash + this.votes1;
        hash = 97 * hash + Objects.hashCode(this.choice2);
        hash = 97 * hash + this.votes2;
        hash = 97 * hash + Objects.hashCode(this.choice3);
        hash = 97 * hash + this.votes3;
        hash = 97 * hash + Objects.hashCode(this.choice4);
        hash = 97 * hash + this.votes4;
        hash = 97 * hash + Objects.hashCode(this.choice5);
        hash = 97 * hash + this.votes5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Survey other = (Survey) obj;
        if (this.surveyId != other.surveyId) {
            return false;
        }
        if (this.votes1 != other.votes1) {
            return false;
        }
        if (this.votes2 != other.votes2) {
            return false;
        }
        if (this.votes3 != other.votes3) {
            return false;
        }
        if (this.votes4 != other.votes4) {
            return false;
        }
        if (this.votes5 != other.votes5) {
            return false;
        }
        if (!Objects.equals(this.question, other.question)) {
            return false;
        }
        if (!Objects.equals(this.choice1, other.choice1)) {
            return false;
        }
        if (!Objects.equals(this.choice2, other.choice2)) {
            return false;
        }
        if (!Objects.equals(this.choice3, other.choice3)) {
            return false;
        }
        if (!Objects.equals(this.choice4, other.choice4)) {
            return false;
        }
        if (!Objects.equals(this.choice5, other.choice5)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mavenproject1.Surveys[ surveyId=" + surveyId + " ]";
    }
    
}
