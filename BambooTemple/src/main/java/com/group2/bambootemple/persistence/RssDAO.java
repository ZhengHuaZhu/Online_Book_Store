package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.Rss;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Mehdi
 */
public interface RssDAO {
    // Create
    public int create(Rss rss) throws SQLException;
    // Read
    public List<Rss> findAll() throws SQLException;
    public Rss findByID(int id) throws SQLException;    
    public Rss findInEffectRss() throws SQLException;    
    // Update
    public int update(Rss rss) throws SQLException;
    // Delete
    public int delete(int id) throws SQLException;    
}
