package com.group2.bambootemple.bean.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;
import javax.servlet.http.Part;
import org.primefaces.model.UploadedFile;

/**
 * @author Julien
 */
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;
    private int bookId;
    private String isbn;
    private String title;
    private int numPages;
    private String author;
    private String publisher;
    private String genre;
    private String eFormat;
    private String description;
    private BigDecimal lPrice;
    private BigDecimal sPrice;
    private BigDecimal wPrice;
    private Date createdOn;
    private boolean removalStatus;
    private UploadedFile image;

    public Inventory() {
    }

    public Inventory(Integer bookId, String isbn, String title, int numPages, String author, String publisher, String genre, String eFormat, String description, BigDecimal lPrice, BigDecimal sPrice, BigDecimal wPrice, Date createdOn, boolean removalStatus) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.numPages = numPages;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.eFormat = eFormat;
        this.description = description;
        this.lPrice = lPrice;
        this.sPrice = sPrice;
        this.wPrice = wPrice;
        this.createdOn = createdOn;
        this.removalStatus = removalStatus;
    }

    public UploadedFile getImage() {
        return image;
    }

    public void setImage(UploadedFile image) {
        this.image = image;
    }    
    
    public int getBookId() {
        return bookId;
    }

    public void setBookId(final int bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(final String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(final int numPages) {
        this.numPages = numPages;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(final String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(final String genre) {
        this.genre = genre;
    }

    public String getEFormat() {
        return eFormat;
    }

    public void setEFormat(final String eFormat) {
        this.eFormat = eFormat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public BigDecimal getLPrice() {
        return lPrice;
    }

    public void setLPrice(final BigDecimal lPrice) {
        this.lPrice = lPrice;
    }

    public BigDecimal getSPrice() {
        return sPrice;
    }

    public void setSPrice(final BigDecimal sPrice) {
        this.sPrice = sPrice;
    }

    public BigDecimal getWPrice() {
        return wPrice;
    }

    public void setWPrice(final BigDecimal wPrice) {
        this.wPrice = wPrice;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(final Date createdOn) {
        this.createdOn = createdOn;
    }

    public boolean getRemovalStatus() {
        return removalStatus;
    }

    public void setRemovalStatus(final boolean removalStatus) {
        this.removalStatus = removalStatus;
    }
    
    public String getDisplayPrice() {
        if(sPrice != null  && !sPrice.equals(new BigDecimal("0.00"))) {
            return sPrice.toString();
        }
        else {
            return lPrice.toString();
        }
    }
    
    public String getRebate() {
        if(sPrice.equals(new BigDecimal("0.00"))) {
            return "";
        }
        else {
            return "-(" + (BigDecimal.ONE.subtract(sPrice.divide(lPrice, 3, RoundingMode.HALF_EVEN))
                    .multiply(BigDecimal.valueOf(100.0)).setScale(2, RoundingMode.HALF_EVEN)).toString() + "%)";
        }
    }

    @Override
    public int hashCode() {
        int hash = Integer.valueOf(bookId);
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
        final Inventory other = (Inventory) obj;
        if (this.isbn == null ? other.isbn != null : !this.isbn.equals(other.isbn)) {
            return false;
        }
        if (this.numPages != other.numPages) {
            return false;
        }
        if (this.removalStatus != other.removalStatus) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.publisher, other.publisher)) {
            return false;
        }
        if (!Objects.equals(this.genre, other.genre)) {
            return false;
        }
        if (!Objects.equals(this.eFormat, other.eFormat)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.bookId, other.bookId)) {
            return false;
        }
        if (this.lPrice.compareTo(other.lPrice) != 0) {
            return false;
        }
        if (this.wPrice.compareTo(other.wPrice) != 0) {
            return false;
        }
        if (this.sPrice.compareTo(other.sPrice) != 0) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Inventory{" + "bookId=" + bookId + ", isbn=" + isbn + ", title=" + title + ", numPages=" + numPages + ", author=" +
                author + ", publisher=" + publisher + ", genre=" + genre + ", eFormat=" + eFormat + ", description=" + description +
                ", lPrice=" + lPrice + ", sPrice=" + sPrice + ", wPrice=" + wPrice + ", createdOn=" + createdOn + ", removalStatus=" + removalStatus + '}';
    }
}
