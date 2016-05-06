package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.Ad;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Julien Comtois
 */
@Named
@SessionScoped
public class SelectedAd implements Serializable {

    private Ad ad;
    
    
    
    public SelectedAd() {
        ad = new Ad();        
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;        
    }

    public void setAdToNew() {
        ad = new Ad();
    }    
}
