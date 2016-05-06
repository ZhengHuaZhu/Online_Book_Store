package com.group2.bambootemple.bean;


import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;


import javax.inject.Named;

/**
 * This backing bean is used to tell which radioButton was selected by a specific report.
 * 
 * @author zhu zhenghua
 */
@Named
@SessionScoped
public class ReportType implements Serializable {
    private String reporttype;

    public String getReporttype() {
        return reporttype;
    }

    public void setReporttype(String reporttype) {
        this.reporttype = reporttype;
    }
}
