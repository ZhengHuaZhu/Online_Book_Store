package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.Tax;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Zhu, Zheng Hua
 */
public interface TaxDAO {
    public int create(Tax tb) throws SQLException;
    public List<Tax> findAll() throws SQLException;
    public Tax findByProvince(String provinceCode) throws SQLException;
    public int update(Tax tb) throws SQLException;
    public int delete(String provinceCode) throws SQLException;
}
