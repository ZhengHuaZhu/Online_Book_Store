/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.sql.DataSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 *
 * @author Zhu, Zheng Hua
 */
@Named
@RequestScoped
public class ItemDAOImpl implements ItemDAO {

    //private final Logger log = LoggerFactory.getLogger(this.getClass()
    //        .getName());
    
    @Resource(name = "java:app/jdbc/myGroup2")
    private DataSource ds;

    public ItemDAOImpl() {
        super();
    }

    @Override
    public int create(Item ib) throws SQLException {
        String query = "INSERT INTO ITEMS (ORDER_ID, BOOK_ID, PRICE, PST, "
                + "GST, HST) VALUES (?,?,?,?,?,?)";
        int result;
        // Connection is only open for the operation and then immediately closed
        try (Connection connection
                = ds.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, ib.getOrderID());
            ps.setInt(2, ib.getBookID());
            ps.setDouble(3, ib.getPrice());
            ps.setDouble(4, ib.getPst());
            ps.setDouble(5, ib.getGst());
            ps.setDouble(6, ib.getHst());
            result = ps.executeUpdate();
        }
        //log.info("an item has been added is (true/false): " + (result > 0));
        return result;
    }

    @Override
    public List<Item> findByOrderID(int id) throws SQLException {
        List<Item> rows = new ArrayList<>();
        String query = "SELECT ITEM_ID, ORDER_ID, BOOK_ID, PRICE, PST, GST, HST "
                + "FROM ITEMS WHERE ORDER_ID = ?";

        // Connection is only open for the operation and then immediately closed
        try (Connection connection = ds.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            // A new try-with-resources block begins for retrieving 
            // the ResultSet object
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(create(resultSet));
                }
            }
        }
        //log.info("The number of items found for the order id is : "
         //       + rows.size());
        return rows;
    }
    
    //added by Mehdi
    @Override
    public List<Item> findByBookID(int id) throws SQLException {
        List<Item> rows = new ArrayList<>();
        String query = "SELECT ITEM_ID, ORDER_ID, BOOK_ID, PRICE, PST, GST, HST "
                + "FROM ITEMS WHERE BOOK_ID = ?";

        // Connection is only open for the operation and then immediately closed
        try (Connection connection = ds.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            // A new try-with-resources block begins for retrieving 
            // the ResultSet object
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(create(resultSet));
                }
            }
        }
        //log.info("The number of items found for the order id is : "
         //       + rows.size());
        return rows;
    }

    @Override
    public Item findByID(int id) throws SQLException {
        Item ib;
        String query = "SELECT ITEM_ID, ORDER_ID, BOOK_ID, PRICE, PST, GST, HST"
                + " FROM ITEMS WHERE ITEM_ID = ?";

        // Connection is only open for the operation and then immediately closed
        try (Connection connection = ds.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            // A new try-with-resources block begins for creating the 
            // ResultSet object
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    ib = create(resultSet);
                } else {
                    ib = null;
                }
            }
        }
        //log.info("an item found by this id is (true/false): " + (ib != null));
        return ib;
    }

    @Override
    public int update(Item ib) throws SQLException {
        String query = "UPDATE ITEMS SET ORDER_ID = ?, BOOK_ID = ?, PRICE = ?, "
                + "PST=?, GST=?, HST=? WHERE ITEM_ID = ?";
        int result;
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = ds.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, ib.getOrderID());
            ps.setInt(2, ib.getBookID());
            ps.setDouble(3, ib.getPrice());
            ps.setDouble(4, ib.getPst());
            ps.setDouble(5, ib.getGst());
            ps.setDouble(6, ib.getHst());
            ps.setInt(7, ib.getItemID());
            result = ps.executeUpdate();
        }
        //log.info("an item has been updated: " + (result > 0));
        return result;
    }

    @Override
    public int delete(int id) throws SQLException {
        int result = 0;
        String query = "DELETE FROM ITEMS WHERE ITEM_ID = ?";

        // Connection is only open for the operation and then immediately closed
        try (Connection connection
                = ds.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            result = ps.executeUpdate();
        }
        //log.info("an item has been deleted: " + (result > 0));
        return result;
    }

    private Item create(ResultSet resultSet) throws SQLException {
        Item ib = new Item();
        ib.setItemID(resultSet.getInt("ITEM_ID"));
        ib.setOrderID(resultSet.getInt("ORDER_ID"));
        ib.setBookID(resultSet.getInt("BOOK_ID"));
        ib.setPrice(resultSet.getDouble("PRICE"));
        ib.setPst(resultSet.getDouble("PST"));
        ib.setGst(resultSet.getDouble("GST"));
        ib.setHst(resultSet.getDouble("HST"));
        return ib;
    }

    @Override
    public List<Item> findAll() throws SQLException {
        List<Item> rows = new ArrayList<>();
        String query = "SELECT ITEM_ID, ORDER_ID, BOOK_ID, PRICE, PST, GST, HST "
                + "FROM ITEMS";

        // Connection is only open for the operation and then immediately closed
        try (Connection connection
                = ds.getConnection();
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
        //log.info("The total number of items : " + rows.size());
        return rows;
    }
    
    public void deleteItem(int id) throws SQLException{
        this.delete(id);
    }
}
