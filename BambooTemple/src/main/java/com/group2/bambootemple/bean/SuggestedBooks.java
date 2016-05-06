package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.Inventory;
import com.group2.bambootemple.bean.entity.Item;
import com.group2.bambootemple.bean.entity.Order;
import com.group2.bambootemple.persistence.InventoryDAOImpl;
import com.group2.bambootemple.persistence.ItemDAOImpl;
import com.group2.bambootemple.persistence.OrderDAOImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This class is the backing bean for suggested books carousel 
 * in the book profile.
 * 
 * @author Marjorie Morales.
 */
@Named
@RequestScoped
public class SuggestedBooks {

    private String bookGenre;
    private List<Inventory> genreBooks;
    private List<Inventory> suggestedAuthorBooks;
    private List<Inventory> suggestedGenreBooks;
    @Inject
    private Login login;
    @Inject
    private ItemDAOImpl itemDAO;
    @Inject
    OrderDAOImpl orderDAO;
    @Inject
    private InventoryAction inventoryAction;
    @Inject
    private InventoryDAOImpl inventoryDAO;

    /**
     * This method gets three books from the same author.
     * @return
     * @throws SQLException 
     */
    public List<Inventory> getSuggestedAuthorBooks() throws SQLException {
        suggestedAuthorBooks = new ArrayList<>();
        /* these lines of code gets three suggested books from the same author.*/
        String bookAuthor = inventoryAction.getInventory().getAuthor();
        //get all the books written by this author.
        List<Inventory> authorBooks = inventoryDAO.findByAuthor(bookAuthor);
        // now add maximum three books written by this author to the suggestedAuthorBooks.
        //check if there is another book written by this author
        if (authorBooks.size() > 1) {
            //if authorBooks size is more than 1, it means has writen other books
            for (int i = 0; i < 3 && i < authorBooks.size(); i++) {
                //check if the book is not the same as the suggested book.
                if (!inventoryAction.getInventory().equals(authorBooks.get(i))) {
                    //if the suggested book has not been bought
                    if (!isBookBought(authorBooks.get(i))) {
                        suggestedAuthorBooks.add(authorBooks.get(i));
                    }
                }
            }
        }
        checkIfThreeAuthorBook();
        return suggestedAuthorBooks;
    }

    /**
     * This method gets three books from the same genre.
     * @return
     * @throws SQLException 
     */
    public List<Inventory> getSuggestedGenreBooks() throws SQLException {
        suggestedGenreBooks = new ArrayList<>();

        //these lines of code gets three or more suggested books from the same genre.
        // get all the books written in the same genre.
        for (int i = 0; suggestedGenreBooks.size() != 3 && i < genreBooks.size(); i++) {
            //check if the book that is being viewed is not the same as the suggested book.
            if (!inventoryAction.getInventory().equals(genreBooks.get(i))) {
                //check if the new suggested book is not yet on the suggestedAuthorBooks list nor on the suggestedGenreBooks list.
                if (!suggestedGenreBooks.contains(genreBooks.get(i)) && !suggestedAuthorBooks.contains(genreBooks.get(i))) {
                    //if the suggested book has not been bought
                    if (!isBookBought(genreBooks.get(i))) {
                        suggestedGenreBooks.add(genreBooks.get(i));
                    }
                }
            }
        }
        return suggestedGenreBooks;
    }

    /**
     * This method adds books from the same genre when the author did not have
     * at least three books written on the database.
     * 
     * @throws SQLException 
     */
    public void checkIfThreeAuthorBook() throws SQLException {
        // check if the suggestedAuthorBooks list has three items, if not add books in same genre instead.
        bookGenre = inventoryAction.getInventory().getGenre();
        genreBooks = inventoryDAO.findByGenre(bookGenre);
        if (suggestedAuthorBooks.size() < 3) {
            for (int i = 0; suggestedAuthorBooks.size() != 3 && i < genreBooks.size(); i++) {
                // if the user almost all the books with that specific genre.
                if (i < 19) {
                    //check if the new suggested book is not the same book that is being viewed.
                    if (!inventoryAction.getInventory().equals(genreBooks.get(i))) {
                        //check if the new suggested book is not yet on the suggestedAuthorBooks list.
                        if (!suggestedAuthorBooks.contains(genreBooks.get(i))) {
                            if (!isBookBought(genreBooks.get(i))) {
                                suggestedAuthorBooks.add(genreBooks.get(i));
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * 
     * This book checks if the book was already bought by the logged in customer.
     * If the suggested book has been bought, do not put in in the suggested
     * carousel.
     * 
     * @param inventory
     * @return
     * @throws SQLException 
     */
    private boolean isBookBought(Inventory inventory) throws SQLException {
        if (login.getUser() != null) {
            int user_id = login.getUser().getUser_id();
            // get all the the orders of that customer
            List<Order> orders = orderDAO.findByUserID(user_id);
            for (Order order : orders) {
                //get the items of an order
                List<Item> items = itemDAO.findByOrderID(order.getOrderID());
                for (Item item : items) {
                    if (inventory.getBookId() == item.getBookID()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
