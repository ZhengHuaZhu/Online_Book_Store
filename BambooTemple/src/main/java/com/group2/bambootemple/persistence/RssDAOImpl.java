package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.SearchQuery;
import com.group2.bambootemple.bean.entity.Rss;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 *
 * @author Mehdi Moodi
 */
@Named
@RequestScoped
public class RssDAOImpl implements RssDAO {
    
    @Resource(name = "java:app/jdbc/myGroup2")
    private DataSource rssSource;
    
    @Inject
    private SearchQuery searchQuery;
    
    public RssDAOImpl() {
    }

    @Override
    public int create(Rss rss) throws SQLException {
        int key = 0;
        String createQuery = "INSERT INTO rss (url, source , isInEffect) VALUES (?,?,?)";
        try (Connection connection = rssSource.getConnection();
                        PreparedStatement ps = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);) {
                ps.setString(1, rss.getUrl());
                ps.setString(2, rss.getSource());                
                ps.setInt(3, rss.getIsInEffect());
                
                ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                key = keys.getInt(1);
        }
        //log.info("id of the record created : " + key);
        return key;
    }
    
    private Rss createRss(ResultSet resultSet) throws SQLException {
        Rss rss = new Rss();
        
        rss.setRss_id(resultSet.getInt("rss_id"));
        rss.setUrl(resultSet.getString("url"));  
        rss.setSource(resultSet.getString("source"));  
        rss.setIsInEffect(resultSet.getInt("isInEffect"));
        return rss;
    }

    @Override
    public List<Rss> findAll() throws SQLException {
        List<Rss> rows = new ArrayList<>();
        String selectQuery = "SELECT * FROM rss";

        try (Connection connection = rssSource.getConnection();          
             PreparedStatement pStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = pStatement.executeQuery()) {
            
            while (resultSet.next()) {
                    rows.add(createRss(resultSet));
            }
        }
        //log.info("# of records found : " + rows.size());
        return rows;
    }

    @Override
    public Rss findByID(int id) throws SQLException {
        Rss survey = new Rss();
        String selectQuery = "SELECT * FROM rss WHERE rss_id = ?";

        try (Connection connection = rssSource.getConnection();          
             PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            
            pStatement.setInt(1, id);
            ResultSet resultSet = pStatement.executeQuery();
            
            while (resultSet.next()) {
                    survey = createRss(resultSet);
            }
        }
        //log.info("# of records found : " + rows.size());
        return survey;
    }

    @Override
    public Rss findInEffectRss() throws SQLException {
        Rss rss = new Rss();
        String selectQuery = "SELECT * FROM rss WHERE isInEffect = 1";

        try (Connection connection = rssSource.getConnection();          
             PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            
            ResultSet resultSet = pStatement.executeQuery();
            
            while (resultSet.next()) {
                    rss = createRss(resultSet);
            }
        }
        //log.info("# of records found : " + rows.size());
        return rss;
    }

    @Override
    public int update(Rss rss) throws SQLException {
        int result = 0;
        String updateQuery = "UPDATE rss SET url=?, source=?, isInEffect=? WHERE rss_id = ?";

        try (Connection connection = rssSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(updateQuery);) {
                ps.setString(1, rss.getUrl());			
                ps.setString(2, rss.getSource());                			
                ps.setInt(3, rss.getIsInEffect());
                ps.setInt(4, rss.getRss_id());
                result = ps.executeUpdate();
        }
        //log.info("# of records updated : " + result);
        return result;
    }

    @Override
    public int delete(int id) throws SQLException {
        int result = 0;
        String deleteQuery = "DELETE FROM rss WHERE rss_id = ?";
        
        try (Connection connection = rssSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(deleteQuery);) {
                ps.setInt(1, id);
                result = ps.executeUpdate();
        }
        //log.info("# of records deleted : " + result);
        return result;
    }
    
    public List<Rss> findBySource() throws SQLException {
        if (searchQuery.getKeyword() == null) {
            searchQuery.setKeyword("");
        }
        List<Rss> rows = new ArrayList<>();
        String selectQuery = "SELECT * FROM rss WHERE source like ?";

        // Using try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = rssSource.getConnection();
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
                    rows.add(createRss(resultSet));
                }
            }
        }
        //log.info("# of records found : " + rows.size());
        return rows;
    }
    
    public void changeStatus(Rss rss) throws SQLException {
        String query;
        if (rss.getIsInEffect() == 0) {
            query = "UPDATE rss SET isInEffect = 1 WHERE rss_id = ?";
        } else {
            query = "UPDATE rss SET isInEffect = 0 WHERE rss_id = ?";
        }

        try (Connection connection = rssSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setInt(1, rss.getRss_id());
            ps.executeUpdate();
        }
    }
}
