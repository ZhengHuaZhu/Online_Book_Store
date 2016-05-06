package com.group2.bambootemple.bean;

import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.chart.PieChartModel;
 
/**
 *
 * @author Julien Comtois
 * @author PrimeFaces
 */

@Named
@SessionScoped
public class ChartView implements Serializable {
    
    @Inject
    private CurrentSurvey currentSurvey;
    
    private PieChartModel pieModel;
 
    public ChartView() {
    }
 
    @PostConstruct
    public void init() {
        createPieModel();
    }
    
    public PieChartModel getPieModel() {
        return pieModel;
    }
 
    private void createPieModel() {
        ArrayList<String> questions = currentSurvey.getQuestions();
        int[] votes = currentSurvey.getVotes();
        
        pieModel = new PieChartModel();
        
        for (int i = 0; i < 5; i++) {
            pieModel.set(questions.get(i), votes[i]);
        }
         
        //pieModel.setTitle("Results");
        pieModel.setLegendPosition("n");
        pieModel.setFill(false);
        pieModel.setShowDataLabels(true);
        pieModel.setDiameter(150);
    }
}