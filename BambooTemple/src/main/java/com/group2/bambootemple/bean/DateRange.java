package com.group2.bambootemple.bean;

import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *  - This class only serves as a backing bean for rendering or taking in components values.
 * - This class is an embedded entity in the OrderDAOImpl class.
 * 
 * @author zhu zhenghua
 */
@Named
@SessionScoped
public class DateRange implements Serializable {
    private Date startdate;
    private Date enddate;
    
    public DateRange(){
        startdate = new Date();
        enddate = new Date();
    }
    
    public Date getStartdate() {
        return startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public boolean checkDaterange() {
        return startdate.after(enddate);
    }  
    
}
