package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.Survey;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Mehdi Moodi
 */
@Named
@SessionScoped
public class SelectedSurvey implements Serializable {

    private Survey survey;
    
    
    
    public SelectedSurvey() {
        survey = new Survey();        
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;        
    }

    public void setSurveyToNew() {
        survey = new Survey();
    }    
}
