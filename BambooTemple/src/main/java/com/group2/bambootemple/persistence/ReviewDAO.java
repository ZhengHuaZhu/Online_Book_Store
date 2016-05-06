/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.Review;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Marjorie Morales
 */
public interface ReviewDAO {
    
    /**
     * Create   -- create a review.
     * @param review
     * @return number of created row.
     * @throws SQLException 
     */
    public int create(Review review) throws SQLException;
    
        /**
    * Read  -- find a review.
    * @param rev_id
    * @return an arraylist of review.
    * @throws SQLException 
    */
    public Review findById(int rev_id) throws SQLException;
    
    /**
    * Read  -- find all approved reviews of a specific book.
    * @param book_id
    * @return an arraylist of review.
    * @throws SQLException 
    */
    public  List<Review> findByBookId(int book_id) throws SQLException;
    
    public  List<Review> findByUserId(int user_id) throws SQLException;
    
    /**
     * Read -  find all reviews that need approval.
     * @return arraylist of review
     * @throws SQLException 
     */
    public List<Review> findReviewsToBeApprove()throws SQLException;
    
    
    /**
     *  Update -- the review's comment
     * @param review_id
     * @return - number of affected row.
     * @throws SQLException 
     */
    public int update(Review review) throws SQLException;
    
    /**
     * Update -- the approval status.
     * @param review_id, comment
     * @return - number of affected row.
     * @throws SQLException 
     */
    public int updateStatus(int review_id, int status) throws SQLException;

    /**
     * Delete --  the review.
     * @param review_id
     * @return -- number of affected row.
     * @throws SQLException 
     */
    public int delete(int review_id) throws SQLException;
    
    public void updateReviewStatus(int review_id, int status) throws SQLException;
}
