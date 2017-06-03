package com.mycompany.adserver.dtos;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class AdvertisingDto implements Serializable{

    private String title;
    private String targetUrl;
    private ImageDto imageDto;
    private AdvertisingCampaignDto advertisingCampaignDto;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public ImageDto getImageDto() {
        return imageDto;
    }

    public void setImageDto(ImageDto imageDto) {
        this.imageDto = imageDto;
    }

    public AdvertisingCampaignDto getAdvertisingCampaignDto() {
        return advertisingCampaignDto;
    }

    public void setAdvertisingCampaignDto(AdvertisingCampaignDto advertisingCampaignDto) {
        this.advertisingCampaignDto = advertisingCampaignDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdvertisingDto that = (AdvertisingDto) o;

        if (advertisingCampaignDto != null ? !advertisingCampaignDto.equals(that.advertisingCampaignDto) : that.advertisingCampaignDto != null)
            return false;
        if (imageDto != null ? !imageDto.equals(that.imageDto) : that.imageDto != null) return false;
        if (targetUrl != null ? !targetUrl.equals(that.targetUrl) : that.targetUrl != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (targetUrl != null ? targetUrl.hashCode() : 0);
        result = 31 * result + (imageDto != null ? imageDto.hashCode() : 0);
        result = 31 * result + (advertisingCampaignDto != null ? advertisingCampaignDto.hashCode() : 0);
        return result;
    }
}
