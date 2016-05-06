package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.Inventory;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author Julien Comtois
 */
public interface InventoryDAO {
    public int create(Inventory inventory) throws SQLException;
    public int delete(int bookId) throws SQLException;
    public List<Inventory> findAll() throws SQLException;
    public List<String> findAllGenres() throws SQLException;
    public List<Inventory> findByAuthor(String author) throws SQLException;
    public Inventory findByBookId(int bookId) throws SQLException;
    public List<Inventory> findByDescription(String description) throws SQLException;
    public List<Inventory> findByEFormat(String eFormat) throws SQLException;
    public List<Inventory> findByGenre(String genre) throws SQLException;
    public List<Inventory> findByIsbn(String isbn) throws SQLException;
    public List<Inventory> findByListPrice(BigDecimal low, BigDecimal high) throws SQLException;
    public List<Inventory> findByNumPages(int low, int high) throws SQLException;
    public List<Inventory> findByPublisher(String publisher) throws SQLException;
    public List<Inventory> findByRemovalStatus(boolean status) throws SQLException;
    public List<Inventory> findBySalePrice(BigDecimal low, BigDecimal high) throws SQLException;
    public List<Inventory> findByTitle(String title) throws SQLException;
    public List<Inventory> findByWholesalePrice(BigDecimal low, BigDecimal high) throws SQLException;
    public List<Inventory> findRecentlyAdded(int limit) throws SQLException;
//    public List<Inventory> getBooks() throws SQLException;
    public List<Inventory> populateTable() throws SQLException;
//    public void setBooks(List<Inventory> books);
    public int update(Inventory inventory) throws SQLException;
    public void updateRemovalStatus(Inventory book) throws SQLException;
}