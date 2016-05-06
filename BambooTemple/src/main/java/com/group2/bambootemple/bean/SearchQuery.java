package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.Inventory;
import com.group2.bambootemple.persistence.InventoryDAOImpl;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 * This backing bean serves as a controller to return different navigation string based on the search word input that is over criteria like title, ISBN, publisher, author, genre & description.
 * 
 * @author 1020645
 * @author Zheng Hua Zhu
 */
@Named(value = "searchQuery")
@SessionScoped
public class SearchQuery implements Serializable {

    @Inject
    private InventoryDAOImpl inventoryDAOImpl;

    @Inject
    InventoryAction inventoryAction;

    private String keyword;
    private String criteria;
    private List<Inventory> books;

    /**
     * Creates a new instance of SearchQuery
     */
    public SearchQuery() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String search() throws SQLException {
        books = getBooks();
        if (books.size() == 1) {
            inventoryAction.setBookId(books.get(0).getBookId());
            return "bookProfile";
        } else {
            return "searchResults";
        }
    }

    public List<Inventory> getBooks() throws SQLException {
        if (criteria == null) {
            books = inventoryDAOImpl.findAll();
        } else {
            switch (criteria.toLowerCase()) {
                case "author":
                    books = inventoryDAOImpl.findByAuthor(keyword);
                    break;
                case "title":
                    books = inventoryDAOImpl.findByTitle(keyword);
                    break;
                case "isbn":
                    books = inventoryDAOImpl.findByIsbn(keyword);
                    break;
                case "description":
                    books = inventoryDAOImpl.findByDescription(keyword);
                    break;
                case "publisher":
                    books = inventoryDAOImpl.findByPublisher(keyword);
                    break;
                case "genre":
                    books = inventoryDAOImpl.findByGenre(keyword);
                    break;
            }
        }
        return books;
    }

}
