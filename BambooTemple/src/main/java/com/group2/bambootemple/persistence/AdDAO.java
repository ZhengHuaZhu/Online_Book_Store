package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.Ad;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Mehdi
 */
public interface AdDAO {
    // Create
    public int create(Ad ad) throws SQLException;
    // Read
    public List<Ad> findAll() throws SQLException;
    public Ad findByID(int id) throws SQLException;    
    public Ad findInEffectAd() throws SQLException;    
    // Update
    public int update(Ad ad) throws SQLException;
    // Delete
    public int delete(int id) throws SQLException;    
}
