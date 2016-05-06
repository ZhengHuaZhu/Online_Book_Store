/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.Order;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Zhu, Zheng Hua
 */
public interface OrderDAO {
    public int create(Order ob) throws SQLException;
    public int delete(int id) throws SQLException;
    public void deleteOrder(int id) throws SQLException;
    public List<Order> findAll() throws SQLException;
    public List<Order> findByDateRange() throws SQLException;
    public Order findByID(int id) throws SQLException;
    public List<Order> findByUserID(int id) throws SQLException;
    public int update(Order ob) throws SQLException;
}