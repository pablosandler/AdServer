package com.mycompany.adserver.dtos;

import java.io.Serializable;

public class ImageDto  implements Serializable {

    private String url;

    private long width;

    private long height;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageDto imageDto = (ImageDto) o;

        if (height != imageDto.height) return false;
        if (width != imageDto.width) return false;
        if (url != null ? !url.equals(imageDto.url) : imageDto.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (int) (width ^ (width >>> 32));
        result = 31 * result + (int) (height ^ (height >>> 32));
        return result;
    }
}
