package com.mycompany.adserver.dtos;

import java.io.Serializable;


public class AdvertisingSpaceDto implements Serializable {

    private String website;

    private long width;

    private long height;


    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }
}
