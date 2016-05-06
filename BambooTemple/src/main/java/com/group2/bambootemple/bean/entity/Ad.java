package com.group2.bambootemple.bean.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Julien Comtois
 */
public class Ad implements Serializable{
    private int ad_id;
    private String url;    
    private String source;
    private int isInEffect;

    public Ad(int ad_id, String url, String source, int isInEffect) {
        this.ad_id = ad_id;
        this.url = url;
        this.source = source;
        this.isInEffect = isInEffect;
    }

    public Ad(){
        this.ad_id = 0;
        this.url = "";
        this.source = "";
        this.isInEffect = 0;
    }

    public int getAd_id() {
        return ad_id;
    }

    public void setAd_id(int ad_id) {
        this.ad_id = ad_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getIsInEffect() {
        return isInEffect;
    }

    public void setIsInEffect(int isInEffect) {
        this.isInEffect = isInEffect;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.ad_id;
        hash = 71 * hash + Objects.hashCode(this.url);
        hash = 71 * hash + Objects.hashCode(this.source);
        hash = 71 * hash + this.isInEffect;
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
        final Ad other = (Ad) obj;
        if (this.ad_id != other.ad_id) {
            return false;
        }
        if (this.isInEffect != other.isInEffect) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ad{" + "ad_id=" + ad_id + ", url=" + url + ", source=" + source + ", isInEffect=" + isInEffect + '}';
    }       
}
