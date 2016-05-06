/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.Item;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Zhu, Zheng Hua
 */
public interface ItemDAO {
    public int create(Item ib) throws SQLException;
    public int delete(int id) throws SQLException;
    public void deleteItem(int id) throws SQLException;
    public List<Item> findAll() throws SQLException;
    public List<Item> findByBookID(int id) throws SQLException;
    public Item findByID(int id) throws SQLException;
    public List<Item> findByOrderID(int id) throws SQLException;
    public int update(Item ib) throws SQLException;
}
