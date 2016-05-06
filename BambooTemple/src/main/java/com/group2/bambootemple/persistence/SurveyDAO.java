package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.Survey;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This is a DAO interface for Survey. It provides CRUD methods in order to 
 * use Surveys within classes. 
 *
 * @author Derek Herbert
 */
public interface SurveyDAO {
    // Create
    public int create(Survey survey) throws SQLException;
    // Read
    public ArrayList<Survey> findAll() throws SQLException;
    public Survey findInEffectSurvey() throws SQLException;
    public Survey findSurveyById(int id) throws SQLException;    
    // Update
    public int update(Survey survey) throws SQLException;
    // Delete
    public int delete(int id) throws SQLException;
}
