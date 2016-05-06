package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.SearchQuery;
import com.group2.bambootemple.bean.entity.Survey;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 * This is the SurveyDAO implementation class. 
 *
 * @author 1235063
 * @author Mehdi Moodi
 */
@Named
@RequestScoped
public class SurveyDAOImpl implements SurveyDAO, Serializable {
    
    @Resource(name = "java:app/jdbc/myGroup2")
    private DataSource surveySource;
    
    @Inject
    private SearchQuery searchQuery;
    
    public SurveyDAOImpl() {
    }
    
    @Override
    public int create(Survey survey) throws SQLException {
        int key = 0;
        String createQuery = "INSERT INTO surveys (question, choice1, votes1, choice2, votes2, choice3, votes3, choice4, votes4, choice5, votes5, isInEffect) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection connection = surveySource.getConnection();
                        PreparedStatement ps = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);) {
                ps.setString(1, survey.getQuestion());
                ps.setString(2, survey.getChoice1());
                ps.setInt(3, survey.getVotes1());
                ps.setString(4, survey.getChoice2());
                ps.setInt(5, survey.getVotes2());
                ps.setString(6, survey.getChoice3());
                ps.setInt(7, survey.getVotes3());
                ps.setString(8, survey.getChoice4());
                ps.setInt(9, survey.getVotes4());
                ps.setString(10, survey.getChoice5());
                ps.setInt(11, survey.getVotes5());
                ps.setInt(12, survey.getIsInEffect());
                
                ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                key = keys.getInt(1);
        }
        //log.info("id of the record created : " + key);
        return key;
    }

    @Override
    public ArrayList<Survey> findAll() throws SQLException {
        ArrayList<Survey> rows = new ArrayList<>();
        String selectQuery = "SELECT survey_id, question, choice1, votes1, choice2, votes2, choice3, votes3, choice4, votes4, choice5, votes5, isInEffect FROM surveys";

        try (Connection connection = surveySource.getConnection();          
             PreparedStatement pStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = pStatement.executeQuery()) {
            
            while (resultSet.next()) {
                    rows.add(createSurvey(resultSet));
            }
        }
        //log.info("# of records found : " + rows.size());
        return rows;
    }
    
    private Survey createSurvey(ResultSet resultSet) throws SQLException {
        Survey survey = new Survey();
        
        survey.setSurveyId(resultSet.getInt("survey_id"));
        survey.setQuestion(resultSet.getString("question"));
        survey.setChoice1(resultSet.getString("choice1"));		
        survey.setVotes1(resultSet.getInt("votes1"));
        survey.setChoice2(resultSet.getString("choice2"));		
        survey.setVotes2(resultSet.getInt("votes2"));
        survey.setChoice3(resultSet.getString("choice3"));		
        survey.setVotes3(resultSet.getInt("votes3"));
        survey.setChoice4(resultSet.getString("choice4"));		
        survey.setVotes4(resultSet.getInt("votes4"));
        survey.setChoice5(resultSet.getString("choice5"));		
        survey.setVotes5(resultSet.getInt("votes5"));
        survey.setIsInEffect(resultSet.getInt("isInEffect"));
        return survey;
    }
    
    @Override
    public Survey findSurveyById(int id) throws SQLException {
        Survey survey = new Survey();
        String selectQuery = "SELECT survey_id, question, choice1, votes1, choice2, votes2, choice3, votes3, choice4, votes4, choice5, votes5, isInEffect "
                           + "FROM surveys "
                           + "WHERE survey_id = ?";

        try (Connection connection = surveySource.getConnection();          
             PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            
            pStatement.setInt(1, id);
            ResultSet resultSet = pStatement.executeQuery();
            
            while (resultSet.next()) {
                    survey = createSurvey(resultSet);
            }
        }
        //log.info("# of records found : " + rows.size());
        return survey;
    }
    
    @Override
    public Survey findInEffectSurvey() throws SQLException {
        Survey survey = new Survey();
        String selectQuery = "SELECT survey_id, question, choice1, votes1, choice2, votes2, choice3, votes3, choice4, votes4, choice5, votes5, isInEffect "
                           + "FROM surveys "
                           + "WHERE isInEffect = 1";

        try (Connection connection = surveySource.getConnection();          
             PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            
            ResultSet resultSet = pStatement.executeQuery();
            
            while (resultSet.next()) {
                    survey = createSurvey(resultSet);
            }
        }
        //log.info("# of records found : " + rows.size());
        return survey;
    }
    
    @Override
    public int update(Survey survey) throws SQLException {
        //System.out.println("survey update got called, isInEffect: "+survey.getIsInEffect());
        int result = 0;
        String updateQuery = "UPDATE surveys SET question=?, choice1=?, votes1=?, choice2=?, votes2=?, choice3=?, votes3=?, choice4=?, votes4=?, choice5=?, votes5=? , isInEffect=? "
                           + "WHERE survey_id = ?";

        try (Connection connection = surveySource.getConnection();
             PreparedStatement ps = connection.prepareStatement(updateQuery);) {
                ps.setString(1, survey.getQuestion());			
                ps.setString(2, survey.getChoice1());
                ps.setInt(3, survey.getVotes1());			
                ps.setString(4, survey.getChoice2());
                ps.setInt(5, survey.getVotes2());			
                ps.setString(6, survey.getChoice3());
                ps.setInt(7, survey.getVotes3());			
                ps.setString(8, survey.getChoice4());
                ps.setInt(9, survey.getVotes4());			
                ps.setString(10, survey.getChoice5());
                ps.setInt(11, survey.getVotes5());			
                ps.setInt(12, survey.getIsInEffect());
                ps.setInt(13, survey.getSurveyId());
                result = ps.executeUpdate();
        }
        //log.info("# of records updated : " + result);
        return result;
    }
    
    @Override
    public int delete(int id) throws SQLException {
        int result = 0;
        String deleteQuery = "DELETE FROM surveys WHERE survey_id = ?";
        
        try (Connection connection = surveySource.getConnection();
             PreparedStatement ps = connection.prepareStatement(deleteQuery);) {
                ps.setInt(1, id);
                result = ps.executeUpdate();
        }
        //log.info("# of records deleted : " + result);
        return result;
    }
    
    public ArrayList<Survey> findAllContainingKeywork() throws SQLException {
        if (searchQuery.getKeyword() == null) {
            searchQuery.setKeyword("");
        }
        ArrayList<Survey> rows = new ArrayList<>();
        String selectQuery = "SELECT * FROM surveys WHERE question like ?";

        // Using try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = surveySource.getConnection();
                // You must use PreparedStatements to guard against SQL
                // Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            // Only object creation statements can be in the parenthesis so
            // first try-with-resources block ends
            pStatement.setString(1, "%" + searchQuery.getKeyword() + "%");
            // A new try-with-resources block for creating the ResultSet object
            // begins
            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(createSurvey(resultSet));
                }
            }
        }
        //log.info("# of records found : " + rows.size());
        return rows;
    }
    
    public void changeStatus(Survey survey) throws SQLException {
        String query;
        if (survey.getIsInEffect() == 0) {
            query = "UPDATE surveys SET isInEffect = 1 WHERE survey_id = ?";
        } else {
            query = "UPDATE surveys SET isInEffect = 0 WHERE survey_id = ?";
        }

        try (Connection connection = surveySource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setInt(1, survey.getSurveyId());
            ps.executeUpdate();
        }
    }
}
