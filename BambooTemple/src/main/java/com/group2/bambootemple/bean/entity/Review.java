package com.group2.bambootemple.bean.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 
 * @author Marjorie Morales
 */
public class Review implements Serializable{
    
    private int reviewId;
    private int userId;
    private int bookId;
    private int rate;
    private String comment;
    //status=0 means that the review is pending
    //status=1 means that the review is approved
    //status=2 means that the review is disapproved
    private int approval_status;
    // the date when the review was created.
    private Date reviewed_on;

    public Review() {       
        this.reviewId = 0;
        this.userId = 0;
        this.bookId = 0;
        this.rate = 0;
        this.comment = "";
        this.approval_status = 0;
        this.reviewed_on = new Date();
    }
    
    
    public Review(int review_id, int user_id, int book_id, int rate, String comment, int approval_status, Date reviewed_on) {
        super();
        this.reviewId = review_id;
        this.userId = user_id;
        this.bookId = book_id;
        this.rate = rate;
        this.comment = comment;
        this.approval_status = approval_status;
        this.reviewed_on = reviewed_on;
    }

    public void setReviewId(int review_id) {
        this.reviewId = review_id;
    }

    public void setUserId(int user_id) {
        this.userId = user_id;
    }

    public void setBookId(int book_id) {
        this.bookId = book_id;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public void setApproval_Status(int approval_status) {
        this.approval_status = approval_status;
    }

    public void setReviewed_On(Date reviewed_on) {
        this.reviewed_on = reviewed_on;
    }

    public int getReviewId() {
        return reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getRate() {
        return rate;
    }
    
    public String getComment() {
        return comment;
    }
    
    public int getApproval_Status() {
        return approval_status;
    }

    public Date getReviewed_On() {
        return reviewed_on;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.reviewId;
        hash = 29 * hash + this.userId;
        hash = 29 * hash + this.bookId;
        hash = 29 * hash + this.rate;
        hash = 29 * hash + Objects.hashCode(this.comment);
        hash = 29 * hash + this.approval_status;
        hash = 29 * hash + Objects.hashCode(this.reviewed_on);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Review other = (Review) obj;
        if (this.reviewId != other.reviewId) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        if (this.bookId != other.bookId) {
            return false;
        }
        if (this.rate != other.rate) {
            return false;
        }
        if (this.approval_status != other.approval_status) {
            return false;
        }
        if (!Objects.equals(this.comment, other.comment)) {
            return false;
        }
        if (!Objects.equals(this.reviewed_on, other.reviewed_on)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Review{" + "review_id=" + reviewId + ", user_id=" + userId + ", book_id=" + bookId + ", rate=" + rate + ", rev_comment=" + comment + ", approval_status=" + approval_status + ", reviewed_on=" + reviewed_on + '}';
    }

}
