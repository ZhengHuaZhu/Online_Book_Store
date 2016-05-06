/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.persistence;


import com.group2.bambootemple.bean.LoginInfo;
import com.group2.bambootemple.bean.SearchQuery;
import com.group2.bambootemple.bean.entity.User;

import java.io.Serializable;
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
 * @author Mehdi
 */
@Named
@RequestScoped
public class UserDAOImpl implements UserDAO, Serializable {

    @Resource(name = "java:app/jdbc/myGroup2")
    private DataSource userSource;
    @Inject
    private SearchQuery searchQuery;
    // added by Zheng Hua for login authorization
    @Inject
    private LoginInfo loginInfo;
    
    public UserDAOImpl() {
        super();
    }

    @Override
    public int create(User user) throws SQLException {
        int key = 0;
        String createQuery = "INSERT INTO users (email,password,fname,lname,company,address1,address2,city,province,country,postalcode,homephone,cellphone,genre) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = userSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFname());
            ps.setString(4, user.getLname());
            ps.setString(5, user.getCompany());
            ps.setString(6, user.getAddress1());
            ps.setString(7, user.getAddress2());
            ps.setString(8, user.getCity());
            ps.setString(9, user.getProvince());
            ps.setString(10, user.getCountry());
            ps.setString(11, user.getPostalcode());
            ps.setString(12, user.getHomephone());
            ps.setString(13, user.getCellphone());
            ps.setString(14, user.getGenre());

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            key = keys.getInt(1);
        }
        //log.info("id of the record created : " + key);
        return key;
    }

    /**
     * This method will return an empty ArrayList is the DB is empty
     *
     * @return
     * @throws SQLException
     */
    @Override
    public ArrayList<User> findAll() throws SQLException {
        ArrayList<User> rows = new ArrayList<>();
        String selectQuery = "SELECT * FROM users";

        // Using try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = userSource.getConnection();
                // You must use PreparedStatements to guard against SQL
                // Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = pStatement.executeQuery()) {
            while (resultSet.next()) {
                rows.add(createUser(resultSet));
            }
        }
        //log.info("# of records found : " + rows.size());
        return rows;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User u = new User();
        u.setUser_id(resultSet.getInt("user_id"));
        u.setEmail(resultSet.getString("email"));
        u.setPassword(resultSet.getString("password"));
        u.setFname(resultSet.getString("fname"));
        u.setLname(resultSet.getString("lname"));
        u.setCompany(resultSet.getString("company"));
        u.setAddress1(resultSet.getString("address1"));
        u.setAddress2(resultSet.getString("address2"));
        u.setCity(resultSet.getString("city"));
        u.setProvince(resultSet.getString("province"));
        u.setCountry(resultSet.getString("country"));
        u.setPostalcode(resultSet.getString("postalcode"));
        u.setHomephone(resultSet.getString("homephone"));
        u.setCellphone(resultSet.getString("cellphone"));
        u.setGenre(resultSet.getString("genre"));
        u.setIsAdmin(resultSet.getInt("isAdmin"));
        return u;
    }

    /**
     * This method will return an array list if no user is found
     *
     * @param email
     * @return
     * @throws SQLException
     */
    @Override
    public ArrayList<User> findByEmail() throws SQLException {
        ArrayList<User> rows = new ArrayList<>();
        String selectQuery = "SELECT * FROM users WHERE email like ?";

        // Using try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = userSource.getConnection();
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
                    rows.add(createUser(resultSet));
                }
            }
        }
        //log.info("# of records found : " + rows.size());
        return rows;
    }

    /**
     * This method will return an array list if no user is found
     *
     * @param lname
     * @return
     * @throws SQLException
     */
    @Override
    public ArrayList<User> findByLastName() throws SQLException {
        ArrayList<User> rows = new ArrayList<>();
        String selectQuery = "SELECT * FROM users WHERE lname like ?";

        // Using try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = userSource.getConnection();
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
                    rows.add(createUser(resultSet));
                }
            }
        }
        //log.info("# of records found : " + rows.size());
        return rows;
    }

    /**
     * This method will return a default user bean if no user is found
     *
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public User findByID(int id) throws SQLException {
        User u = new User();
        String selectQuery = "SELECT * FROM users WHERE user_id=?";
        try (Connection connection = userSource.getConnection();
                // You must use PreparedStatements to guard against SQL
                // Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            // Only object creation statements can be in the parenthesis so
            // first try-with-resources block ends
            pStatement.setInt(1, id);
            // A new try-with-resources block for creating the ResultSet object
            // begins
            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    u = createUser(resultSet);
                }
            }
        }
        //log.info("Found " + id + "?: " + (rb.getId() != 0));
        return u;
    }

    @Override
    public int update(User user) throws SQLException {
        int result = 0;
        String updateQuery = "UPDATE users SET email=?, password=?, fname=?, lname=?, company=?, address1=?, address2=?, city=?, province=?, country=?, postalcode=?, homephone=?, cellphone=?, genre=? WHERE user_id = ?";

        // Connection is only open for the operation and then immediately closed
        try (Connection connection = userSource.getConnection();
                // You must use a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(updateQuery);) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFname());
            ps.setString(4, user.getLname());
            ps.setString(5, user.getCompany());
            ps.setString(6, user.getAddress1());
            ps.setString(7, user.getAddress2());
            ps.setString(8, user.getCity());
            ps.setString(9, user.getProvince());
            ps.setString(10, user.getCountry());
            ps.setString(11, user.getPostalcode());
            ps.setString(12, user.getHomephone());
            ps.setString(13, user.getCellphone());
            ps.setString(14, user.getGenre());
            ps.setInt(15, user.getUser_id());
            result = ps.executeUpdate();
        }
        //log.info("# of records updated : " + result);
        return result;
    }

    @Override
    public int delete(int id) throws SQLException {
        int result = 0;
        String deleteQuery = "DELETE FROM users WHERE user_id = ?";
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = userSource.getConnection();
                // You must use PreparedStatements to guard against SQL
                // Injection
                PreparedStatement ps = connection.prepareStatement(deleteQuery);) {
            ps.setInt(1, id);
            result = ps.executeUpdate();
        }
        //log.info("# of records deleted : " + result);
        return result;
    }

    public ArrayList<User> populateTable() throws SQLException {
        //System.out.println("populateTable was called!");
        if (searchQuery.getCriteria() == null) {            
            return this.findAll();
        } else if (searchQuery.getCriteria().equals("LastName")) {
            return this.findByLastName();
        } else {
            return this.findByEmail();
        }
    }

    public String deleteUser(int id) throws SQLException {
        this.delete(id);
        return "management";
    }
    
    // added by Zheng Hua for authorization
    @Override
    public User findByEmailPassword() throws SQLException {
        User user = null;
        String selectQuery = "SELECT * FROM users WHERE email=? and password=?";
        try (Connection connection = userSource.getConnection();
                // You must use PreparedStatements to guard against SQL
                // Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            // Only object creation statements can be in the parenthesis so
            // first try-with-resources block ends
            pStatement.setString(1, loginInfo.getEmail());
            pStatement.setString(2, loginInfo.getPassword());
            // A new try-with-resources block for creating the ResultSet object
            // begins
            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = createUser(resultSet);
                }
            }
        }
        //log.info("Found " + id + "?: " + (rb.getId() != 0));
        return user;
    }

    @Override
    public boolean checkExistingUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email=?";

        // Using try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = userSource.getConnection();
            // You must use PreparedStatements to guard against SQL
            // Injection
            PreparedStatement pStatement = connection.prepareStatement(query);) {
                // Only object creation statements can be in the parenthesis so
                // first try-with-resources block ends
                pStatement.setString(1, email);
                // A new try-with-resources block for creating the ResultSet object
                // begins
                try (ResultSet resultSet = pStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return true;
                    }
                }
        }
        //log.info("# of records found : " + rows.size());
        return false;
    }
    
    /**
     * This method will return a User if found
     *
     * @param email
     * @return
     * @throws SQLException
     */
    public User findByUniqueEmail(String email) throws SQLException {
        User client = null;
        String selectQuery = "SELECT * FROM users WHERE email like ?";

        // Using try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = userSource.getConnection();
                // You must use PreparedStatements to guard against SQL
                // Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            // Only object creation statements can be in the parenthesis so
            // first try-with-resources block ends
            pStatement.setString(1, email);
            // A new try-with-resources block for creating the ResultSet object
            // begins
            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    client = createUser(resultSet);
                }
            }
        }
        //log.info("# of records found : " + rows.size());
        return client;
    }
    
    public List<String> findAllEmails() throws SQLException {
        List<String> rows = new ArrayList<>();
        String selectQuery = "SELECT email FROM users";

        // Using try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = userSource.getConnection();
                // You must use PreparedStatements to guard against SQL
                // Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = pStatement.executeQuery()) {
            while (resultSet.next()) {
                rows.add(resultSet.getString("email"));
            }
        }
        //log.info("# of records found : " + rows.size());
        return rows;
    }

    @Override
    public User findByUniqueEmail() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void changeStatus(User user) throws SQLException {
        String query;
        if (user.getIsAdmin() == 0) {
            query = "UPDATE users SET isAdmin = 1 WHERE user_id = ?";
        } else {
            query = "UPDATE users SET isAdmin = 0 WHERE user_id = ?";
        }

        try (Connection connection = userSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setInt(1, user.getUser_id());
            ps.executeUpdate();
        }
    }
}
