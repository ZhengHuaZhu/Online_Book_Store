package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.Review;
import com.group2.bambootemple.persistence.ReviewDAOImpl;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * 
 * This purpose of this backing bean is to display all approved reviews of a book.
 * Also, allow a user to create a review, edit an existing review or delete. 
 * 
 * @author Marjorie Morales
 */
@Named
@RequestScoped
public class ReviewAction implements Serializable {

    @Inject
    private Login user;
    private int bookAverageRate;
    @Inject
    private ReviewDAOImpl reviewDAO;
    private Review review = new Review();
    @Inject
    private InventoryAction inventoryAction;
    private List<Review> bookReviews = new ArrayList<Review>();

    /**
     * Get a review
     * @return a review bean
     */
    public Review getReview() {
        return review;
    }

    /**
     * Get all the approved reviews of the viewed book.
     * 
     * @return List<Review> reviews
     * @throws SQLException 
     */
    public List<Review> getBookReviews() throws SQLException {
        bookReviews = reviewDAO.findByBookId(inventoryAction.getBookId());
        return bookReviews;
    }
    /**
     * This method adds a review to the database.
     * @return empty
     * @throws SQLException 
     */
    public String addAction() throws SQLException {
        review.setBookId(inventoryAction.getBookId());
        review.setUserId(user.getUser().getUser_id());
        int result = reviewDAO.create(review);
        return "";
    }

    /**
     * This method edits a review.
     * @return null - return to the same page.
     * @throws SQLException 
     */
    public String updateAction() throws SQLException {
        if (review != null) {
            // check if something is change
            Review origReview = reviewDAO.findById(review.getReviewId());
            if((origReview.getRate() != review.getRate()) || !(origReview.getComment().equals(review.getComment()))){
                //if something is change, update the review on the database.
                int result = reviewDAO.update(review);
            }
        }
        return null;
    }

    /**
     * This method deletes a review from the database.
     * 
     * @param reviewId
     * @return
     * @throws SQLException 
     */
    public String deleteAction(int reviewId) throws SQLException {
        int result = reviewDAO.delete(reviewId);
        return "";
    }
    
    /**
     * This method checks if a user already written a review for this book.
     * @return
     * @throws SQLException 
     */
    public boolean isUserReviewExists() throws SQLException {
        if (user.getUser() != null) {
            List<Review> reviews = reviewDAO.findByUserId(user.getUser().getUser_id());
            // checks if this user has already witten a review.      
            if (reviews == null) {
                return false;
            } else {
                // check if the user has already written a review on this book
                for (Review userReview : reviews) {
                    if (userReview.getBookId() == inventoryAction.getBookId()) {
                        review = userReview;
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }
    /**
     * Checks if the review written on this book has been approved.
     * @return 
     */
    public boolean isApprove() {
        int approvalStatus = review.getApproval_Status();
        switch (approvalStatus) {
            case 0:
                return false;
            case 1:
                return true;
            default:
                return false;
        }
    }
    /**
     * Get the book's average review rating.
     * @return int book review average.
     * @throws SQLException 
     */
    public int getBookAverageRate() throws SQLException {
        bookReviews = reviewDAO.findByBookId(inventoryAction.getBookId());
        this.bookAverageRate = 0;
        int totalRate = 0;
        int numReview = bookReviews.size();
        //if there is a review
        if (numReview != 0) {
            for (int i = 0; i < numReview; i++) {
                totalRate += bookReviews.get(i).getRate();
            }
            bookAverageRate = Math.round(totalRate / numReview);
        }

        return bookAverageRate;
    }
}
