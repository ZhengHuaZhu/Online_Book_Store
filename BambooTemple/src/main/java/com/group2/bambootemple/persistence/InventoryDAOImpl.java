package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.Inventory;
import com.group2.bambootemple.bean.SearchQuery;
import com.group2.bambootemple.bean.SelectedBook;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * @author Julien Comtois
 * @author Zheng Hua Zhu
 * @author Mehdi Moodi
 */
@Named
@RequestScoped
public class InventoryDAOImpl implements InventoryDAO, Serializable {

    @Resource(name = "java:app/jdbc/myGroup2")
    private DataSource inventorySource;

    private List<Inventory> books;
    @Inject
    private SearchQuery sq;
    @Inject
    private SelectedBook sb;

    public InventoryDAOImpl() {
        super();
    }

    /**
     * Add a book to the DB
     *
     * @param inventory The book to add
     * @return the id of the book
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public int create(Inventory inventory) throws SQLException {
        String createQuery = "INSERT INTO inventory (isbn, title, num_pages, author, publisher, genre, e_format, description, l_price, w_price) VALUES (?,?,?,?,?,?,?,?,?,?)";
        int key = 0;
        try (Connection connection = inventorySource.getConnection();
                PreparedStatement ps = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, inventory.getIsbn());
            ps.setString(2, inventory.getTitle());
            ps.setInt(3, inventory.getNumPages());
            ps.setString(4, inventory.getAuthor());
            ps.setString(5, inventory.getPublisher());
            ps.setString(6, inventory.getGenre());
            ps.setString(7, inventory.getEFormat());
            ps.setString(8, inventory.getDescription());
            ps.setBigDecimal(9, inventory.getLPrice());
            ps.setBigDecimal(10, inventory.getWPrice());

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            key = keys.getInt(1);
        }
        saveImage(inventory.getImage(), inventory.getIsbn());
        return key;
    }

    /**
     * Get all books which aren't removed
     *
     * @return all books which aren't removed
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public List<Inventory> findAll() throws SQLException {
        String query = "SELECT * FROM inventory where removal_status = 0 ";
        books = new ArrayList<>();
        try (Connection connection = inventorySource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Inventory book = createBook(rs);
                books.add(book);
            }
        }
        return books;
    }

    /**
     * Get complete list of genres
     *
     * @return list of genres
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public List<String> findAllGenres() throws SQLException {
        String query = "SELECT DISTINCT genre FROM inventory";
        ArrayList<String> genres = new ArrayList<>();
        try (Connection connection = inventorySource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String genre = rs.getString("genre");
                if (!genre.isEmpty()) {
                    genres.add(genre);
                }
            }
        }
        return genres;
    }

    /**
     * Find a book by its id
     *
     * @param bookId the id of the book
     * @return the book
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public Inventory findByBookId(int bookId) throws SQLException {
        return findByInt(bookId, "book_id");
    }

    /**
     * Find books by their isbn
     *
     * @param isbn the book's isbn
     * @return the books
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public List<Inventory> findByIsbn(String isbn) throws SQLException {
        return findByString(isbn, "isbn");
    }

    /**
     * Find books by their title
     *
     * @param title Title of the book
     * @return the books
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public List<Inventory> findByTitle(String title) throws SQLException {
        return findByString(title, "title");
    }

    /**
     * Find books by their number of pages in a range
     *
     * @param low low bound
     * @param high high bound
     * @return the books
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public List<Inventory> findByNumPages(int low, int high) throws SQLException {
        String query = "SELECT * FROM inventory WHERE num_pages BETWEEN ? AND ?";
        books = new ArrayList<>();
        try (Connection connection = inventorySource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setInt(1, low);
            ps.setInt(2, high);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Inventory book = createBook(rs);
                books.add(book);
            }
        }
        return books;
    }

    /**
     * Find books by their author
     *
     * @param author the author
     * @return the books
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public List<Inventory> findByAuthor(String author) throws SQLException {
        return findByString(author, "author");
    }

    /**
     * Find books by their publisher
     *
     * @param publisher the publisher
     * @return the books
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public List<Inventory> findByPublisher(String publisher) throws SQLException {
        return findByString(publisher, "publisher");
    }

    /**
     * Find books by their description
     *
     * @param description the description
     * @return the books
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public List<Inventory> findByDescription(String description) throws SQLException {
        return findByString(description, "description");
    }

    /**
     * Find books by their genre
     *
     * @param genre the genre
     * @return the books
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public List<Inventory> findByGenre(String genre) throws SQLException {
        return findByString(genre, "genre");
    }

    /**
     * Find books by their format
     *
     * @param eFormat the format
     * @return the books
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public List<Inventory> findByEFormat(String eFormat) throws SQLException {
        return findByString(eFormat, "e_format");
    }

    /**
     * Find books by their list price in a range
     *
     * @param low low bound
     * @param high high bound
     * @return the books
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public List<Inventory> findByListPrice(BigDecimal low, BigDecimal high) throws SQLException {
        return findByBigDecimal(low, high, "l_price");
    }

    /**
     * Find books by their sale price in a range
     *
     * @param low low bound
     * @param high high bound
     * @return the books
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public List<Inventory> findBySalePrice(BigDecimal low, BigDecimal high) throws SQLException {
        return findByBigDecimal(low, high, "s_price");
    }

    /**
     * Find books by their wholesale price in a range
     *
     * @param low low bound
     * @param high high bound
     * @return the books
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public List<Inventory> findByWholesalePrice(BigDecimal low, BigDecimal high) throws SQLException {
        return findByBigDecimal(low, high, "w_price");
    }

    /**
     * Find books by their removal status
     * @param status the tatus
     * @return the books
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public List<Inventory> findByRemovalStatus(boolean status) throws SQLException {
        String query = "SELECT * FROM inventory WHERE removal_status = ?";
        books = new ArrayList<>();
        try (Connection connection = inventorySource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setBoolean(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Inventory book = createBook(rs);
                books.add(book);
            }
        }
        return books;
    }

    /**
     * Find books that were recently added up to a limit
     * @param limit the limit
     * @return the books
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public List<Inventory> findRecentlyAdded(int limit) throws SQLException {
        String query = "SELECT * FROM inventory ORDER BY book_id DESC LIMIT ?";
        books = new ArrayList<>();
        try (Connection connection = inventorySource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Inventory book = createBook(rs);
                books.add(book);
            }
        }
        return books;
    }

    /**
     * Updates a book in the DB
     * @param inventory the book to update
     * @return rows affected
     * @throws SQLException
     * @author Julien Comtois
     *
     * Modified by Mehdi
     */
    @Override
    public int update(Inventory inventory) throws SQLException {
        String query = "UPDATE inventory SET isbn = ?, title = ?, num_pages = ?, author = ?, publisher = ?, genre = ?, "
                + "e_format = ?, description = ?, l_price = ?, s_price = ?, w_price = ?, created_on = ? WHERE book_id = ?";
        int count = 0;
        try (Connection connection = inventorySource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setString(1, inventory.getIsbn());
            ps.setString(2, inventory.getTitle());
            ps.setInt(3, inventory.getNumPages());
            ps.setString(4, inventory.getAuthor());
            ps.setString(5, inventory.getPublisher());
            ps.setString(6, inventory.getGenre());
            ps.setString(7, inventory.getEFormat());
            ps.setString(8, inventory.getDescription());
            ps.setBigDecimal(9, inventory.getLPrice());
            ps.setBigDecimal(10, inventory.getSPrice());
            ps.setBigDecimal(11, inventory.getWPrice());
            //for some wierd reason I have to add a day, otherwise it registers to the previous day
            ps.setDate(12, new java.sql.Date(inventory.getCreatedOn().getTime() + (24 * 60 * 60 * 1000)));
            ps.setInt(13, inventory.getBookId());
            count = ps.executeUpdate();
        }
        saveImage(inventory.getImage(), inventory.getIsbn());
        return count;
    }

    /**
     * Delete a book from the DB
     * @param bookId the id of the book to delete
     * @return the rows affected
     * @throws SQLException
     * @author Julien Comtois
     */
    @Override
    public int delete(int bookId) throws SQLException {
        String query = "UPDATE inventory SET removal_status = true WHERE book_id = ?";
        int count = 0;
        try (Connection connection = inventorySource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setInt(1, bookId);
            count = ps.executeUpdate();
        }
        return count;
    }

    /**
     * Create an Inventory object representing a book
     * @param rs The result set
     * @return The book
     * @throws SQLException
     * @author Julien Comtois
     */
    public Inventory createBook(ResultSet rs) throws SQLException {
        Inventory book = new Inventory();
        book.setBookId(rs.getInt("book_id"));
        book.setIsbn(rs.getString("isbn"));
        book.setTitle(rs.getString("title"));
        book.setNumPages(rs.getInt("num_pages"));
        book.setAuthor(rs.getString("author"));
        book.setPublisher(rs.getString("publisher"));
        book.setGenre(rs.getString("genre"));
        book.setEFormat(rs.getString("e_format"));
        book.setDescription(rs.getString("description"));
        book.setLPrice(rs.getBigDecimal("l_price"));
        book.setWPrice(rs.getBigDecimal("w_price"));
        book.setSPrice(rs.getBigDecimal("s_price"));
        if (rs.getInt("removal_status") == 0) {
            book.setRemovalStatus(false);
        } else {
            book.setRemovalStatus(true);
        }
        book.setCreatedOn(rs.getTimestamp("created_on"));
        return book;
    }

    /**
     * Common code to find books by a string and a column name
     * @param param the string to search
     * @param column the column name
     * @return The books found
     * @throws SQLException
     * @author Julien Comtois
     */
    private List<Inventory> findByString(String param, String column) throws SQLException {
        String query = "SELECT * FROM inventory WHERE " + column + " LIKE ? and "
                + "removal_status = 0 ";
        books = new ArrayList<>();
        try (Connection connection = inventorySource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setString(1, "%" + param + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Inventory book = createBook(rs);
                books.add(book);
            }
        }
        return books;
    }

    /**
     * Common code to find books by an int and a column name
     * @param param the int to search
     * @param column the column name
     * @return
     * @throws SQLException
     * @author Julien Comtois
     */
    private Inventory findByInt(int param, String column) throws SQLException {
        String query = "SELECT * FROM inventory WHERE " + column + " = ?";
        Inventory book = new Inventory();
        try (Connection connection = inventorySource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setInt(1, param);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                book = createBook(rs);
            }
        }
        return book;
    }

    /**
     * Common code to find books in a range of BigDecimals
     * @param low the low bound
     * @param high the high bound
     * @param column the column name
     * @return the books
     * @throws SQLException
     * @author Julien Comtois
     */
    private List<Inventory> findByBigDecimal(BigDecimal low, BigDecimal high, String column) throws SQLException {
        String query = "SELECT * FROM inventory WHERE " + column + " BETWEEN ? AND ?";
        books = new ArrayList<>();
        try (Connection connection = inventorySource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setBigDecimal(1, low);
            ps.setBigDecimal(2, high);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Inventory book = createBook(rs);
                books.add(book);
            }
        }
        return books;
    }

    //Added by Mehdi
    public List<Inventory> populateTable() throws SQLException {
        if (sq.getCriteria() == null) {
            return this.findAll();
        } else if (sq.getCriteria().equals("Title")) {
            return this.findByTitle(sq.getKeyword());
        } else if (sq.getCriteria().equals("Author")) {
            return this.findByAuthor(sq.getKeyword());
        } else if (sq.getCriteria().equals("Publisher")) {
            return this.findByPublisher(sq.getKeyword());
        } else if (sq.getCriteria().equals("Description")) {
            return this.findByDescription(sq.getKeyword());
        } else {
            return this.findByIsbn(sq.getKeyword());
        }
    }

    public List<String> findAuthors() throws SQLException {
        String query = "SELECT DISTINCT author FROM inventory";
        List<String> authors = new ArrayList<>();
        try (Connection connection = inventorySource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String author = rs.getString("author");
                if (!author.isEmpty()) {
                    authors.add(author);
                }
            }
        }
        return authors;
    }

    public List<String> findPublishers() throws SQLException {
        String query = "SELECT DISTINCT publisher FROM inventory";
        List<String> rows = new ArrayList<>();
        try (Connection connection = inventorySource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String row = rs.getString("publisher");
                if (!row.isEmpty()) {
                    rows.add(row);
                }
            }
        }
        return rows;
    }

    public void updateRemovalStatus(Inventory book) throws SQLException {
        String query;
        if (book.getRemovalStatus() == false) {
            query = "UPDATE inventory SET removal_status = 1 WHERE book_id = ?";
        } else {
            query = "UPDATE inventory SET removal_status = 0 WHERE book_id = ?";
        }

        try (Connection connection = inventorySource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setInt(1, book.getBookId());
            ps.executeUpdate();
        }
    }

    private void saveImage(UploadedFile image, String isbn) {
        if (image != null) {
            String filePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/bookCovers");
            String extension = FilenameUtils.getExtension(image.getFileName());
            try (InputStream input = image.getInputstream();
                    OutputStream output = new FileOutputStream(new File(filePath, isbn + "." + extension));) {
                IOUtils.copy(input, output);
            } catch (IOException ex) {
                Logger.getLogger(InventoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void upload(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        sb.getInventory().setImage(uploadedFile);
        System.out.println(uploadedFile.getFileName());
    }
}
