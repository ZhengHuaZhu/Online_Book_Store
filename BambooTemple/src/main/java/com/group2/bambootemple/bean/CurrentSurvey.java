package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.Survey;
import com.group2.bambootemple.persistence.SurveyDAOImpl;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Keeps a survey object and records client survey votes.
 *
 * @author Julien Comtois
 */
@Named
@SessionScoped
public class CurrentSurvey implements Serializable {

    @Inject
    private SurveyDAOImpl surveyDAOImpl;

    private Survey survey;

    private String selected;

    private boolean hasVoted;
    
    public CurrentSurvey() throws SQLException {
    }

    /**
     * Initializes the survey field from the DB.
     */
    @PostConstruct
    public void init() {
        try {
            survey = surveyDAOImpl.findInEffectSurvey();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentSurvey.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isHasVoted() {
        return hasVoted;
    }
    
    public boolean isHasNotVoted() {
        return !hasVoted;
    }
    
    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    /**
     * Makes ArrayList of the survey questions and returns it.
     *
     * @return Survey questions
     */
    public ArrayList<String> getQuestions() {
        ArrayList<String> questions = new ArrayList<String>();
        questions.add(survey.getChoice1());
        questions.add(survey.getChoice2());
        questions.add(survey.getChoice3());
        questions.add(survey.getChoice4());
        questions.add(survey.getChoice5());
        return questions;
    }
    
    /**
     * Makes array of the survey vote counts and returns it.
     *
     * @return Survey vote counts
     */
    public int[] getVotes() {
        int[] votes = new int[5];
        votes[0] = survey.getVotes1();
        votes[1] = survey.getVotes2();
        votes[2] = survey.getVotes3();
        votes[3] = survey.getVotes4();
        votes[4] = survey.getVotes5();
        return votes;
    }

    /**
     * Increments vote count based on user's choice.
     *
     * @param vote Which of the 5 choices was picked
     * @return empty string to stay on same page
     */
    public void submitSurvey() throws SQLException {
        switch (selected) {
            case "1":
                survey.setVotes1(survey.getVotes1() + 1);
                break;
            case "2":
                survey.setVotes2(survey.getVotes2() + 1);
                break;
            case "3":
                survey.setVotes3(survey.getVotes3() + 1);
                break;
            case "4":
                survey.setVotes4(survey.getVotes4() + 1);
                break;
            case "5":
                survey.setVotes5(survey.getVotes5() + 1);
                break;
        }
        surveyDAOImpl.update(survey);
        hasVoted = true;
        //return "";
    }
}
