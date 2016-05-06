/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.Review;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 *
 * @author Marjorie Morales
 */
@Named
@RequestScoped
public class ReviewDAOImpl implements ReviewDAO, Serializable {

    private static final Logger log = Logger.getLogger(ReviewDAOImpl.class.getName());

    @Resource(name = "java:app/jdbc/myGroup2")
    private DataSource reviewDataSource;

    private List<Review> reviews;

    public ReviewDAOImpl() {
        super();
    }
    /**
     * Add a review to the Review table.
     * @param review
     * @return
     * @throws SQLException 
     */
    @Override
    public int create(Review review) throws SQLException {
        int result = 0;
        String createQuery = "INSERT INTO REVIEWS(USER_ID, BOOK_ID,RATE, REV_COMMENT) VALUES (?,?,?,?) ";
        try (Connection connection = reviewDataSource.getConnection();
                PreparedStatement reviewStatement = connection.prepareStatement(createQuery);) {
            reviewStatement.setInt(1, review.getUserId());
            reviewStatement.setInt(2, review.getBookId());
            reviewStatement.setInt(3, review.getRate());
            reviewStatement.setString(4, review.getComment());
            result = reviewStatement.executeUpdate();
        }
        return result;
    }
    
    /**
     * Get a review.
     * @param rev_id
     * @return
     * @throws SQLException 
     */
    @Override
    public Review findById(int rev_id) throws SQLException {
        Review review = new Review();
        String reviewQuery = "SELECT * FROM REVIEWS WHERE REVIEW_ID=?";
        try (Connection connection = reviewDataSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(reviewQuery);) {
            pStatement.setInt(1, rev_id);
            ResultSet resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                review = createReviewBean(resultSet);
            }
        }
        return review;
    }

    /**
     * Get all approved reviews of a specific book.
     * @param book_id
     * @return
     * @throws SQLException 
     */
    @Override
    public List<Review> findByBookId(int book_id) throws SQLException {
        reviews = new ArrayList<>();
        String reviewQuery = "SELECT * FROM REVIEWS WHERE BOOK_ID=? AND APPROVAL_STATUS = 1";
        try (Connection connection = reviewDataSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(reviewQuery);) {
            pStatement.setInt(1, book_id);
            ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                reviews.add(createReviewBean(resultSet));
            }
        }
        //log.info("# of records found : " + rows.size());
        return reviews;
    }
    
    /**
     * Get all reviews of a book.
     * @param book_id
     * @return
     * @throws SQLException 
     */
    //Added by Mehdi - Needed for management
    public List<Review> findAllByBookId(int book_id) throws SQLException {
        reviews = new ArrayList<>();
        String reviewQuery = "SELECT * FROM REVIEWS WHERE BOOK_ID=?";
        try (Connection connection = reviewDataSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(reviewQuery);) {
            pStatement.setInt(1, book_id);
            ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                reviews.add(createReviewBean(resultSet));
            }
        }
        //log.info("# of records found : " + rows.size());
        return reviews;
    }
    /**
     * Get all the reviews written by a user(customer).
     * @param user_id
     * @return List<Review> reviews
     * @throws SQLException 
     */
    @Override
    public List<Review> findByUserId(int user_id) throws SQLException {
        reviews = new ArrayList<>();
        String reviewQuery = "SELECT * FROM REVIEWS WHERE USER_ID=?";
        try (Connection connection = reviewDataSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(reviewQuery);) {
            pStatement.setInt(1, user_id);
            ResultSet resultSet = pStatement.executeQuery();

            while (resultSet.next()) {
                reviews.add(createReviewBean(resultSet));
            }
        }
        //log.info("# of records found : " + rows.size());
        return reviews;
    }

    /**
     * Get all reviews that need approval.
     * @return List<Review> reviews
     * @throws SQLException 
     */
    @Override
    public List<Review> findReviewsToBeApprove() throws SQLException {
        reviews = new ArrayList<>();
        String reviewQuery = "SELECT * FROM REVIEWS WHERE APPROVAL_STATUS=0";
        try (Connection connection = reviewDataSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(reviewQuery);
                ResultSet resultSet = pStatement.executeQuery()) {
            while (resultSet.next()) {
                reviews.add(createReviewBean(resultSet));
            }
        }
        log.info("# of records found : " + reviews.size());
        return reviews;
    }

    /**
     * Update a review
     * @param review
     * @return number of updated row.
     * @throws SQLException 
     */
    @Override
    public int update(Review review) throws SQLException {
        int result = 0;
        String updateNameQuery = "UPDATE REVIEWS SET REV_COMMENT=?, RATE=?, APPROVAL_STATUS=0 WHERE REVIEW_ID=?";
        try (Connection connection = reviewDataSource.getConnection();
                PreparedStatement update = connection.prepareStatement(updateNameQuery);) {
            update.setString(1, review.getComment());
            update.setInt(2, review.getRate());
            update.setInt(3, review.getReviewId());
            result = update.executeUpdate();
        }
        log.info("# of records updated : " + result);
        return result;
    }
    /**
     * Change the status of a review.
     * @param review_id
     * @param status
     * @return number of updated row.
     * @throws SQLException 
     */
    @Override
    public int updateStatus(int review_id, int status) throws SQLException {
        int result = 0;
        String updateNameQuery = "UPDATE REVIEWS SET APPROVAL_STATUS=? WHERE REVIEW_ID=?";
        try (Connection connection = reviewDataSource.getConnection();
                PreparedStatement update = connection.prepareStatement(updateNameQuery);) {
            update.setInt(1, status);
            update.setInt(2, review_id);
            result = update.executeUpdate();
        }
        log.info("# of records updated : " + result);
        return result;
    }

    /**
     * Delete a review
     * @param review_id
     * @return number of deleted row.
     * @throws SQLException 
     */
    @Override
    public int delete(int review_id) throws SQLException {
        int result = 0;
        String deleteQuery = "DELETE FROM REVIEWS WHERE REVIEW_ID = ?";
        try (Connection connection = reviewDataSource.getConnection();
                PreparedStatement delete = connection.prepareStatement(deleteQuery);) {
            delete.setInt(1, review_id);
            result = delete.executeUpdate();

            log.info("# of records deleted : " + result);
            return result;
        }
    }

    /**
     * Private method that creates an object of type Review from the current
     * record in the ResultSet
     *
     * @param resultSet
     * @return a review
     * @throws SQLException
     */
    private Review createReviewBean(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        review.setReviewId(resultSet.getInt("REVIEW_ID"));
        review.setUserId(resultSet.getInt("USER_ID"));
        review.setBookId(resultSet.getInt("BOOK_ID"));
        review.setRate(resultSet.getInt("RATE"));
        review.setComment(resultSet.getString("REV_COMMENT"));
        review.setApproval_Status(resultSet.getInt("APPROVAL_STATUS"));
        Timestamp stamp = resultSet.getTimestamp("REVIEWED_ON");
        Date date = new Date(stamp.getTime());
        review.setReviewed_On(date);
        return review;
    }
    // added by Mehdi
    public void deleteReview(int review_id) throws SQLException {
        this.delete(review_id);
    }
    // added by Mehdi
    public void updateReviewStatus(int review_id, int status) throws SQLException {
        this.updateStatus(review_id, status);
    }
}
