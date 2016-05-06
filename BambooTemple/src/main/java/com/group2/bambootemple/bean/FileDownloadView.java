package com.group2.bambootemple.bean;

import java.io.InputStream;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
 
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
 
/**
 * This method is use to download the a book's pdf file.
 * 
 * @author Marjorie Morales
 */
@Named
@RequestScoped
public class FileDownloadView {
    
    private StreamedContent file;
    private String bookTitle;
 
    /**
     * Get the pdf file
     * @return 
     */
    public StreamedContent getFile() {
        InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/resources/pdf/Alice_in_Wonderland.pdf");
        file = new DefaultStreamedContent(stream, "pdf", bookTitle + ".pdf");
        return file;
    }
    /**
     * Set the book's title as the pdf download name.
     * @param bookTitle 
     */
    public void setBookTitle(String bookTitle){
        this.bookTitle = bookTitle;
    }
}