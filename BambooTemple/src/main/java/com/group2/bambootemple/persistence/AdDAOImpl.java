package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.SearchQuery;
import com.group2.bambootemple.bean.entity.Ad;
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
 * @author Julien Comtois
 */
@Named
@RequestScoped
public class AdDAOImpl implements AdDAO {
    
    @Resource(name = "java:app/jdbc/myGroup2")
    private DataSource adSource;
    
    @Inject
    private SearchQuery searchQuery;
    
    public AdDAOImpl() {
    }

    @Override
    public int create(Ad ad) throws SQLException {
        int key = 0;
        String createQuery = "INSERT INTO ad (url, source , isInEffect) VALUES (?,?,?)";
        try (Connection connection = adSource.getConnection();
                        PreparedStatement ps = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);) {
                ps.setString(1, ad.getUrl());
                ps.setString(2, ad.getSource());                
                ps.setInt(3, ad.getIsInEffect());
                
                ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                key = keys.getInt(1);
        }
        //log.info("id of the record created : " + key);
        return key;
    }
    
    private Ad createAd(ResultSet resultSet) throws SQLException {
        Ad ad = new Ad();
        
        ad.setAd_id(resultSet.getInt("ad_id"));
        ad.setUrl(resultSet.getString("url"));  
        ad.setSource(resultSet.getString("source"));  
        ad.setIsInEffect(resultSet.getInt("isInEffect"));
        return ad;
    }

    @Override
    public List<Ad> findAll() throws SQLException {
        List<Ad> rows = new ArrayList<>();
        String selectQuery = "SELECT * FROM ad";

        try (Connection connection = adSource.getConnection();          
             PreparedStatement pStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = pStatement.executeQuery()) {
            
            while (resultSet.next()) {
                    rows.add(createAd(resultSet));
            }
        }
        //log.info("# of records found : " + rows.size());
        return rows;
    }

    @Override
    public Ad findByID(int id) throws SQLException {
        Ad survey = new Ad();
        String selectQuery = "SELECT * FROM ad WHERE ad_id = ?";

        try (Connection connection = adSource.getConnection();          
             PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            
            pStatement.setInt(1, id);
            ResultSet resultSet = pStatement.executeQuery();
            
            while (resultSet.next()) {
                    survey = createAd(resultSet);
            }
        }
        //log.info("# of records found : " + rows.size());
        return survey;
    }

    @Override
    public Ad findInEffectAd() throws SQLException {
        Ad ad = new Ad();
        String selectQuery = "SELECT * FROM ad WHERE isInEffect = 1";

        try (Connection connection = adSource.getConnection();          
             PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            
            ResultSet resultSet = pStatement.executeQuery();
            
            while (resultSet.next()) {
                    ad = createAd(resultSet);
            }
        }
        //log.info("# of records found : " + rows.size());
        return ad;
    }

    @Override
    public int update(Ad ad) throws SQLException {
        int result = 0;
        String updateQuery = "UPDATE ad SET url=?, source=?, isInEffect=? WHERE ad_id = ?";

        try (Connection connection = adSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(updateQuery);) {
                ps.setString(1, ad.getUrl());			
                ps.setString(2, ad.getSource());                			
                ps.setInt(3, ad.getIsInEffect());
                ps.setInt(4, ad.getAd_id());
                result = ps.executeUpdate();
        }
        //log.info("# of records updated : " + result);
        return result;
    }

    @Override
    public int delete(int id) throws SQLException {
        int result = 0;
        String deleteQuery = "DELETE FROM ad WHERE ad_id = ?";
        
        try (Connection connection = adSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(deleteQuery);) {
                ps.setInt(1, id);
                result = ps.executeUpdate();
        }
        //log.info("# of records deleted : " + result);
        return result;
    }
    
    public List<Ad> findBySource() throws SQLException {
        if (searchQuery.getKeyword() == null) {
            searchQuery.setKeyword("");
        }
        List<Ad> rows = new ArrayList<>();
        String selectQuery = "SELECT * FROM ad WHERE source like ?";

        // Using try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = adSource.getConnection();
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
                    rows.add(createAd(resultSet));
                }
            }
        }
        //log.info("# of records found : " + rows.size());
        return rows;
    }
    
    public void changeStatus(Ad ad) throws SQLException {
        String query;
        if (ad.getIsInEffect() == 0) {
            query = "UPDATE ad SET isInEffect = 1 WHERE ad_id = ?";
        } else {
            query = "UPDATE ad SET isInEffect = 0 WHERE ad_id = ?";
        }

        try (Connection connection = adSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setInt(1, ad.getAd_id());
            ps.executeUpdate();
        }
    }
}
