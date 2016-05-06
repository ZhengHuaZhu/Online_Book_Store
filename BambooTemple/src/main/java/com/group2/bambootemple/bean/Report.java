package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.Inventory;
import com.group2.bambootemple.persistence.InventoryDAOImpl;
import com.group2.bambootemple.persistence.ItemDAOImpl;
import com.group2.bambootemple.persistence.OrderDAOImpl;
import com.group2.bambootemple.persistence.UserDAOImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This is a backing bean served as a controller to render different sales reports based on client, author, publisher, sales records and a report summary.
 * It basically takes in user's selection on the radioButton component and the drop-down menu. 
 * 
 * @author zhu zhenghua
 */
@Named
@SessionScoped
public class Report implements Serializable {

    @Inject
    private OrderDAOImpl orderInstance;

    @Inject
    private ReportType reporttype;

    @Inject
    private DateRange dr;

    private List<TopSeller> topsellers;
    private List<ClientSalesReport> topclients;
    private List<Inventory> zerosales;
    private ClientSalesReport clientreport;
    private AuthorSalesReport authorreport;
    private PublisherSalesReport publisherreport;
    private TotalSales totalsales;
    private String clientemail;
    private String author;
    private String publisher;
    private boolean wrongdaterange;

    public void getReports() throws SQLException {
            switch (reporttype.getReporttype().toLowerCase()) {
                case "sales by client":
                    reportSalesByClient();
                    break;
                case "sales by author":
                    reportSalesByAuthor();
                    break;
                case "sales by publisher":
                    reportSalesByPublisher();
                    break;
                case "total sales":
                    reportTotalsales();
                    break;
                case "top sellers":
                    reportTopsellers();
                    break;
                case "top clients":
                    reportTopclients();
                    break;
                case "zero sales":
                    reportZerosellers();
                    break;
            }
            wrongdaterange=dr.checkDaterange();
    }

    public boolean isWrongdaterange() {
        return wrongdaterange;
    }

    public List<TopSeller> getTopsellers() {
        return topsellers;
    }

    public List<ClientSalesReport> getTopclients() {
        return topclients;
    }

    public List<Inventory> getZerosales() {
        return zerosales;
    }

    public ClientSalesReport getClientreport() {
        return clientreport;
    }

    public AuthorSalesReport getAuthorreport() {
        return authorreport;
    }

    public PublisherSalesReport getPublisherreport() {
        return publisherreport;
    }

    public TotalSales getTotalsales() {
        return totalsales;
    }

    public String getClientemail() {
        return clientemail;
    }

    public void setClientemail(String clientemail) {
        this.clientemail = clientemail;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    private void reportSalesByClient() throws SQLException {
        clientreport = orderInstance.findByUserEmailAndDateRange(clientemail);
    }

    private void reportSalesByAuthor() throws SQLException {
        authorreport = orderInstance.findByAuthorAndDateRange(author);
    }

    private void reportSalesByPublisher() throws SQLException {
        publisherreport = orderInstance.findByPublisherAndDateRange(publisher);
    }

    private void reportTopsellers() throws SQLException {
        topsellers = orderInstance.findTopsellersAndDateRange();
    }

    private void reportTopclients() throws SQLException {
        topclients = orderInstance.findTopclientsAndDateRange();
    }

    private void reportZerosellers() throws SQLException {
        zerosales = orderInstance.findZerosalesAndDateRange();
    }

    private void reportTotalsales() throws SQLException {
        totalsales = orderInstance.findTotalsalesAndDateRange();
    }

}
