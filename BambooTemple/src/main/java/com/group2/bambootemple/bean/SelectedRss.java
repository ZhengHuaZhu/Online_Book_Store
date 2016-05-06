package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.Rss;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Mehdi Moodi
 */
@Named
@SessionScoped
public class SelectedRss implements Serializable {

    private Rss rss;
    
    
    
    public SelectedRss() {
        rss = new Rss();        
    }

    public Rss getRss() {
        return rss;
    }

    public void setRss(Rss rss) {
        this.rss = rss;        
    }

    public void setRssToNew() {
        rss = new Rss();
    }    
}
