package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.Tax;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 *
 * @author 1334262
 */
@Named
@RequestScoped
public class TaxDAOImpl implements TaxDAO, Serializable{
    
    //private final Logger log = LoggerFactory.getLogger(this.getClass()
	//		.getName());
    
//    private final String host="jdbc:mysql://localhost:3306/group2";
//    private final String user="root";
//    private final String password="";
    
    @Resource(name = "java:app/jdbc/myGroup2")
    private DataSource taxSource;
	
    public TaxDAOImpl() {
        super();
    }

    @Override
    public int create(Tax tb) throws SQLException {
        String query = "INSERT INTO TAXES (PROVINCE, PST, GST, HST) "
                + "VALUES (?,?,?,?)";
	int result;	
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = 
//                DriverManager.getConnection(host, user, password);
            taxSource.getConnection();
            // Using a prepared statement to handle the conversion
            // of special characters in the SQL statement and guard against 
            // SQL Injection
            PreparedStatement ps = connection.prepareStatement(query)){
                ps.setString(1, tb.getProvince());
                ps.setDouble(2, tb.getPst());
                ps.setDouble(3, tb.getGst());
                ps.setDouble(4, tb.getHst());
		result = ps.executeUpdate();
        }
        //log.info("a tax record has been added is (true/false): " + (result>0));
        return result;    
    }

    @Override
    public Tax findByProvince(String provinceCode) throws SQLException {
        Tax tb;
        String query = "SELECT TAX_ID, PROVINCE, PST, GST, HST "
                + "FROM TAXES WHERE PROVINCE = ?";
	
	// Connection is only open for the operation and then immediately closed
        try (Connection connection = 
//                DriverManager.getConnection(host, user, password);
                taxSource.getConnection();
            // Using a prepared statement to handle the conversion
            // of special characters in the SQL statement and guard against 
            // SQL Injection
            PreparedStatement ps = connection.prepareStatement(query)){
                ps.setString(1, provinceCode);
                // A new try-with-resources block begins for creating the 
                // ResultSet object
                try (ResultSet resultSet = ps.executeQuery()) {
                    if (resultSet.next()) {
                        tb = create(resultSet);
                    }else{
                        tb = null;
                    }
                }
        }
        //log.info("a tax record found by province code: "+ provinceCode + " is (true/false): " + 
        //        (tb != null));
        return tb;
    }

    @Override
    public int update(Tax tb) throws SQLException {
        String query = "UPDATE TAXES SET PROVINCE = ?, PST = ?, GST = ?, "
                + "HST = ? WHERE PROVINCE = ?";
        int result;	
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = 
//                DriverManager.getConnection(host, user, password);
                taxSource.getConnection();
            // Using a prepared statement to handle the conversion
            // of special characters in the SQL statement and guard against 
            // SQL Injection
            PreparedStatement ps = connection.prepareStatement(query)){
                ps.setString(1, tb.getProvince());
                ps.setDouble(2, tb.getPst());
                ps.setDouble(3, tb.getGst());
                ps.setDouble(4, tb.getHst());
                ps.setString(5, tb.getProvince());
		result = ps.executeUpdate();
        }
        //log.info("a tax record has been updated is (true/false): " + (result>0));
        return result;          
     
    }

    @Override
    public int delete(String provinceCode) throws SQLException {
        int result = 0;
	String query = "DELETE FROM TAXES WHERE PROVINCE = ?";

        // Connection is only open for the operation and then immediately closed
        try (Connection connection = 
//                DriverManager.getConnection(host, user, password);
                taxSource.getConnection();
            // Using a prepared statement to handle the conversion
            // of special characters in the SQL statement and guard against 
            // SQL Injection
            PreparedStatement ps = connection.prepareStatement(query)){
                ps.setString(1, provinceCode);
                result = ps.executeUpdate();
        }
        //log.info("an order has been deleted: " + (result>0));
        return result;    
    }
    
    private Tax create(ResultSet resultSet) throws SQLException {
        Tax tb = new Tax();
        tb.setTaxID(resultSet.getInt("TAX_ID"));
        tb.setProvince(resultSet.getString("PROVINCE"));
        tb.setPst(resultSet.getDouble("PST"));
        tb.setGst(resultSet.getDouble("GST"));
        tb.setHst(resultSet.getDouble("HST"));
        return tb;
    }

    @Override
    public List<Tax> findAll() throws SQLException {
       List<Tax> rows = new ArrayList<>();
        String query = "SELECT TAX_ID, PROVINCE, PST, GST, HST"
                + " FROM TAXES";

        // Connection is only open for the operation and then immediately closed
        try (Connection connection
//                = DriverManager.getConnection(host, user, password);
                = taxSource.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query);
                // A new try-with-resources block begins for retrieving 
                // the ResultSet object
                ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                rows.add(create(resultSet));
            }
        }
        //log.info("The total number of provincial taxes found : " + rows.size());
        return rows;
    }
    
}