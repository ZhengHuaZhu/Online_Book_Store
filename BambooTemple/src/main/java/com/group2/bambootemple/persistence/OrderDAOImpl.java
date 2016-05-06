/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.AuthorSalesReport;
import com.group2.bambootemple.bean.ClientSalesReport;
import com.group2.bambootemple.bean.entity.Order;
import com.group2.bambootemple.bean.DateRange;
import com.group2.bambootemple.bean.entity.Inventory;
import com.group2.bambootemple.bean.PublisherSalesReport;
import com.group2.bambootemple.bean.TopSeller;
import com.group2.bambootemple.bean.TotalSales;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 *
 * @author zhu zhenghua
 */
@Named
@RequestScoped
public class OrderDAOImpl implements OrderDAO, Serializable {

    @Inject
    private DateRange dr;

    @Inject
    private InventoryDAOImpl inventoryInstance;

    @Resource(name = "java:app/jdbc/myGroup2")
    private DataSource ds;

    public OrderDAOImpl() {
        super();
    }
    
    private boolean wrongdaterange;

    public boolean isWrongdaterange() {
        return wrongdaterange;
    }

    public void setWrongdaterange(boolean wrongdaterange) {
        this.wrongdaterange = wrongdaterange;
    }

    @Override
    public int create(Order ob) throws SQLException {
        String query = "INSERT INTO ORDERS (USER_ID, ORDERED_ON) "
                + "VALUES (?,?)";
        int result;
        // Connection is only open for the operation and then immediately closed
        try (Connection connection
                = ds.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, ob.getUserID());
            ps.setTimestamp(2, Timestamp.valueOf(ob.getOrderedON()));
            result = ps.executeUpdate();
        }
        //log.info("an order has been added is (true/false): " + (result > 0));
        return result;
    }

    @Override
    public List<Order> findByUserID(int id) throws SQLException {
        List<Order> rows = new ArrayList<>();
        String query = "SELECT ORDER_ID, USER_ID, ORDERED_ON"
                + " FROM ORDERS WHERE USER_ID = ?";

        // Connection is only open for the operation and then immediately closed
        try (Connection connection
                = ds.getConnection();
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
        //log.info("The number of orders found for user id: " + id + " is : "
        //       + rows.size());
        return rows;
    }

    @Override
    public Order findByID(int id) throws SQLException {
        Order ob;
        String query = "SELECT ORDER_ID, USER_ID, ORDERED_ON "
                + "FROM ORDERS WHERE ORDER_ID = ?";

        // Connection is only open for the operation and then immediately closed
        try (Connection connection
                = ds.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            // A new try-with-resources block begins for creating the 
            // ResultSet object
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    ob = create(resultSet);
                } else {
                    ob = null;
                }
            }
        }
        //log.info("an order found by order id " + id + " is (true/false): "
        //       + (ob != null));
        return ob;
    }

    @Override
    public int update(Order ob) throws SQLException {
        String query = "UPDATE ORDERS SET USER_ID = ?, ORDERED_ON = ? "
                + "WHERE ORDER_ID = ?";
        int result;
        // Connection is only open for the operation and then immediately closed
        try (Connection connection
                = ds.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, ob.getUserID());
            ps.setTimestamp(2, Timestamp.valueOf(ob.getOrderedON()));
            ps.setInt(3, ob.getOrderID());
            result = ps.executeUpdate();
        }
        //log.info("an order has been updated is (true/false): " + (result > 0));
        return result;
    }

    @Override
    public int delete(int id) throws SQLException {
        int result = 0;
        String query = "DELETE FROM ORDERS WHERE ORDER_ID = ?";

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
        //log.info("an order has been deleted: " + (result > 0));
        return result;
    }

    private Order create(ResultSet resultSet) throws SQLException {
        Order ob = new Order();
        ob.setOrderID(resultSet.getInt("ORDER_ID"));
        ob.setUserID(resultSet.getInt("USER_ID"));
        ob.setOrderedON(resultSet.getTimestamp("ORDERED_ON").toLocalDateTime());
        return ob;
    }

    @Override
    public List<Order> findAll() throws SQLException {
        List<Order> rows = new ArrayList<>();
        String query = "SELECT ORDER_ID, USER_ID, ORDERED_ON"
                + " FROM ORDERS";

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
        //log.info("The total number of orders found : " + rows.size());
        return rows;
    }

    public void deleteOrder(int id) throws SQLException {
        this.delete(id);
    }

    public List<Order> findByDateRange() throws SQLException {
        List<Order> rows = new ArrayList<>();
        String query = "SELECT ORDER_ID, USER_ID, ORDERED_ON"
                + " FROM ORDERS where ORDERED_ON between ? and ? ";

        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
        String startdate = formatter.format(dr.getStartdate());
        String enddate = formatter.format(dr.getEnddate());

        // Connection is only open for the operation and then immediately closed
        try (Connection connection
                = ds.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query)) {
            // A new try-with-resources block begins for retrieving 
            // the ResultSet object
            ps.setString(1, startdate);
            ps.setString(2, enddate);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(create(resultSet));
                }
            }
        }
        //log.info("The total number of orders found : " + rows.size());
        return rows;
    }

    public ClientSalesReport findByUserEmailAndDateRange(String email) throws SQLException {
        ClientSalesReport row = new ClientSalesReport();
        String query = "SELECT email, CONCAT(fname,' ',lname) fullname, sum(price) purchase, "
                + "sum(w_price) cost, (sum(price)-sum(w_price)) profit "
                + "FROM users JOIN orders USING(user_id) "
                + "JOIN items USING(order_id) "
                + "JOIN inventory USING(book_id) "
                + "WHERE ordered_on BETWEEN ? AND ? "
                + "AND email = ? ";

        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
        String startdate = formatter.format(dr.getStartdate());
        String enddate = formatter.format(dr.getEnddate());

        // Connection is only open for the operation and then immediately closed
        try (Connection connection
                = ds.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query)) {
            // A new try-with-resources block begins for retrieving 
            // the ResultSet object
            ps.setString(1, startdate);
            ps.setString(2, enddate);
            ps.setString(3, email);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    row.setEmail(resultSet.getString("email"));
                    row.setName(resultSet.getString("fullname"));
                    row.setSales(BigDecimal.valueOf(resultSet.getDouble("purchase")));
                    row.setCost(BigDecimal.valueOf(resultSet.getDouble("cost")));
                    row.setProfit(BigDecimal.valueOf(resultSet.getDouble("profit")));
                }
            }
        }
        //log.info("The total number of orders found : " + rows.size());
        return row;
    }

    public AuthorSalesReport findByAuthorAndDateRange(String author) throws SQLException {
        AuthorSalesReport row = new AuthorSalesReport();
        String query = "SELECT count(author) book_sold, author, sum(price) sales, "
                + "sum(w_price) cost, (sum(price)-sum(w_price)) profit "
                + "FROM inventory JOIN items USING(book_id) "
                + "JOIN orders USING(order_id) "
                + "WHERE ordered_on BETWEEN ? AND ? "
                + "AND author = ? ";

        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
        String startdate = formatter.format(dr.getStartdate());
        String enddate = formatter.format(dr.getEnddate());

        // Connection is only open for the operation and then immediately closed
        try (Connection connection
                = ds.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query)) {
            // A new try-with-resources block begins for retrieving 
            // the ResultSet object
            ps.setString(1, startdate);
            ps.setString(2, enddate);
            ps.setString(3, author);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    row.setQuantity(resultSet.getString("book_sold"));
                    row.setAuthor(resultSet.getString("author"));
                    row.setSales(BigDecimal.valueOf(resultSet.getDouble("sales")));
                    row.setCost(BigDecimal.valueOf(resultSet.getDouble("cost")));
                    row.setProfit(BigDecimal.valueOf(resultSet.getDouble("profit")));
                }
            }
        }
        //log.info("The total number of orders found : " + rows.size());
        return row;
    }

    public PublisherSalesReport findByPublisherAndDateRange(String publisher) throws SQLException {
        PublisherSalesReport row = new PublisherSalesReport();
        String query = "SELECT count(publisher) book_sold, publisher, sum(price) sales, "
                + "sum(w_price) cost, (sum(price)-sum(w_price)) profit "
                + "FROM inventory JOIN items USING(book_id) "
                + "JOIN orders USING(order_id) "
                + "WHERE ordered_on BETWEEN ? AND ? "
                + "AND publisher = ? ";

        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
        String startdate = formatter.format(dr.getStartdate());
        String enddate = formatter.format(dr.getEnddate());

        // Connection is only open for the operation and then immediately closed
        try (Connection connection
                = ds.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query)) {
            // A new try-with-resources block begins for retrieving 
            // the ResultSet object
            ps.setString(1, startdate);
            ps.setString(2, enddate);
            ps.setString(3, publisher);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    row.setQuantity(resultSet.getString("book_sold"));
                    row.setPublisher(resultSet.getString("publisher"));
                    row.setSales(BigDecimal.valueOf(resultSet.getDouble("sales")));
                    row.setCost(BigDecimal.valueOf(resultSet.getDouble("cost")));
                    row.setProfit(BigDecimal.valueOf(resultSet.getDouble("profit")));
                }
            }
        }
        //log.info("The total number of orders found : " + rows.size());
        return row;
    }

    public List<TopSeller> findTopsellersAndDateRange() throws SQLException {
        List<TopSeller> rows = new ArrayList<>();
        String query = "SELECT title, ISBN, count(book_id) qty, sum(price) sales, sum(w_price) cost, "
                + "(sum(price)-sum(w_price)) profit FROM inventory JOIN items USING(book_id) "
                + "JOIN orders USING(order_id) "
                + "WHERE ordered_on BETWEEN ? AND ? "
                + "group by title order by count(book_id) desc";

        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
        String startdate = formatter.format(dr.getStartdate());
        String enddate = formatter.format(dr.getEnddate());

        // Connection is only open for the operation and then immediately closed
        try (Connection connection
                = ds.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query)) {
            // A new try-with-resources block begins for retrieving 
            // the ResultSet object
            ps.setString(1, startdate);
            ps.setString(2, enddate);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    TopSeller row = new TopSeller();
                    row.setTitle(resultSet.getString("title"));
                    row.setISBN(resultSet.getString("ISBN"));
                    row.setQty(resultSet.getInt("qty"));
                    row.setSales(BigDecimal.valueOf(resultSet.getDouble("sales")));
                    row.setCost(BigDecimal.valueOf(resultSet.getDouble("cost")));
                    row.setProfit(BigDecimal.valueOf(resultSet.getDouble("profit")));
                    rows.add(row);
                }
            }
        }
        //log.info("The total number of orders found : " + rows.size());
        return rows;
    }

    public List<ClientSalesReport> findTopclientsAndDateRange() throws SQLException {
        List<ClientSalesReport> rows = new ArrayList<>();
        String query = "SELECT email, concat(fname,' ',lname) fullname, sum(price) sales, sum(w_price) cost, "
                + "(sum(price)-sum(w_price)) profit FROM users JOIN orders USING(user_id) "
                + "JOIN items USING(order_id) JOIN inventory USING(book_id) "
                + "WHERE ordered_on BETWEEN ? AND ? "
                + "group by email order by sum(price) desc";

        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
        String startdate = formatter.format(dr.getStartdate());
        String enddate = formatter.format(dr.getEnddate());

        // Connection is only open for the operation and then immediately closed
        try (Connection connection
                = ds.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query)) {
            // A new try-with-resources block begins for retrieving 
            // the ResultSet object
            ps.setString(1, startdate);
            ps.setString(2, enddate);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    ClientSalesReport row = new ClientSalesReport();
                    row.setEmail(resultSet.getString("email"));
                    row.setName(resultSet.getString("fullname"));
                    row.setSales(BigDecimal.valueOf(resultSet.getDouble("sales")));
                    row.setCost(BigDecimal.valueOf(resultSet.getDouble("cost")));
                    row.setProfit(BigDecimal.valueOf(resultSet.getDouble("profit")));
                    rows.add(row);
                }
            }
        }
        //log.info("The total number of orders found : " + rows.size());
        return rows;
    }

    public List<Inventory> findZerosalesAndDateRange() throws SQLException {
        List<Inventory> rows = new ArrayList<>();
        String query = "SELECT * FROM inventory WHERE book_id NOT IN "
                + "(SELECT book_id FROM items JOIN orders USING (order_id) "
                + "WHERE ordered_on BETWEEN ? AND ?) ";

        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
        String startdate = formatter.format(dr.getStartdate());
        String enddate = formatter.format(dr.getEnddate());

        // Connection is only open for the operation and then immediately closed
        try (Connection connection
                = ds.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query)) {
            // A new try-with-resources block begins for retrieving 
            // the ResultSet object
            ps.setString(1, startdate);
            ps.setString(2, enddate);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    rows.add(inventoryInstance.createBook(resultSet));
                }
            }
        }
        //log.info("The total number of orders found : " + rows.size());
        return rows;
    }

    public TotalSales findTotalsalesAndDateRange() throws SQLException {
        TotalSales row = new TotalSales();
        String query = "SELECT count(book_id) qty, sum(price) sales, sum(w_price) cost, (sum(price)-sum(w_price)) profit "
                + "FROM users JOIN orders USING(user_id) "
                + "JOIN items USING(order_id) "
                + "JOIN inventory USING(book_id) "
                + "WHERE ordered_on BETWEEN ? AND ? ";

        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
        String startdate = formatter.format(dr.getStartdate());
        String enddate = formatter.format(dr.getEnddate());

        // Connection is only open for the operation and then immediately closed
        try (Connection connection
                = ds.getConnection();
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against 
                // SQL Injection
                PreparedStatement ps = connection.prepareStatement(query)) {
            // A new try-with-resources block begins for retrieving 
            // the ResultSet object
            ps.setString(1, startdate);
            ps.setString(2, enddate);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    row.setSales(BigDecimal.valueOf(resultSet.getDouble("sales")));
                    row.setCost(BigDecimal.valueOf(resultSet.getDouble("cost")));
                    row.setProfit(BigDecimal.valueOf(resultSet.getDouble("profit")));
                    row.setQty(resultSet.getInt("qty"));
                }
            }
        }
        //log.info("The total number of orders found : " + rows.size());
        return row;
    }

    public int getLastId() throws SQLException {
        long id = 0;
        String query = "SELECT LAST_INSERT_ID() FROM ORDERS";
        // Connection is only open for the operation and then immediately closed
        try (Connection connection
                = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next())
                id = resultSet.getLong("last_insert_id()"); 
        }
        //log.info("an order has been added is (true/false): " + (result > 0));
        return (int) id;
    }
    
    
}
