package com.group2.bambootemple.bean;

import java.util.Locale;
import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * @author Julien Comtois
 */
@ManagedBean
@SessionScoped
public class LocaleAction {

    private Locale locale;

    @PostConstruct
    public void init() {
        locale = new Locale("en");
    }

    public String getLanguage() {
        return locale.getDisplayLanguage();
    }

    public String getOtherLanguage() {
        if (locale.equals(new Locale("en"))) {
            return "French";
        } else {
            return "English";
        }
    }

    public String getOtherLocale() {
        if (locale.equals(new Locale("en"))) {
            return "fr";
        } else {
            return "en";
        }
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

    public void switchLocale() {
        if (locale.equals(new Locale("en"))) {
            locale = new Locale("fr");
        } else {
            locale = new Locale("en");
        }
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}
