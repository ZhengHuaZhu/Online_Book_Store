package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mehdi
 */
public interface UserDAO {
    // Create
    public int create(User user) throws SQLException;
    // Read
    public List<User> findAll() throws SQLException;
    public List<User> findByEmail() throws SQLException;
    public List<User> findByLastName() throws SQLException;
    public User findByEmailPassword() throws SQLException; //added by Zheng Hua for login authorization
    public User findByID(int id) throws SQLException;    
    public User findByUniqueEmail() throws SQLException;
    public List<User> populateTable() throws SQLException;
    // Update
    public int update(User user) throws SQLException;
    // Delete
    public int delete(int id) throws SQLException;
    public String deleteUser(int id) throws SQLException;
    // Validate
    public boolean checkExistingUserByEmail(String email) throws SQLException;
}
