package com.mycompany.adserver.entities;

import com.mycompany.adserver.dtos.AdvertisingDto;
import org.apache.cxf.common.util.StringUtils;

import javax.persistence.*;

@Entity
@Table(name = "advertising")
public final class Advertising {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable=false)
    private final Long id;

    @Column(nullable=false)
    private final String title;

    @Column(nullable=false)
    private final String targetUrl;

    @ManyToOne(fetch=FetchType.LAZY,optional=false,cascade = CascadeType.ALL)
    @JoinColumn(name="image_id")
    private final Image image;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="campaign_id")
    private AdvertisingCampaign advertisingCampaign;

    public Advertising(String title, String targetUrl, Image image, AdvertisingCampaign advertisingCampaign) {
        if(StringUtils.isEmpty(title)){
            throw new IllegalArgumentException("Title cannot be null");
        }

        if(StringUtils.isEmpty(targetUrl)){
            throw new IllegalArgumentException("Target URL cannot be null");
        }

        if(image==null){
            throw new IllegalArgumentException("image cannot be null");
        }

        this.id = null;
        this.title = title;
        this.targetUrl = targetUrl;
        this.image = image;
        this.advertisingCampaign = advertisingCampaign;
    }

    public Advertising(AdvertisingDto advertisingDto, AdvertisingCampaign advertisingCampaign) {
        id=null;

        Image image = new Image(advertisingDto.getImageDto());
        this.image= image;

        this.title=advertisingDto.getTitle();
        this.targetUrl=advertisingDto.getTargetUrl();
        this.advertisingCampaign= advertisingCampaign;
    }


    public void setAdvertisingCampaign(AdvertisingCampaign advertisingCampaign) {
        this.advertisingCampaign = advertisingCampaign;
    }

    public AdvertisingCampaign getAdvertisingCampaign() {
        return advertisingCampaign;
    }

    public AdvertisingDto getDto() {
        AdvertisingDto advertisingDto = new AdvertisingDto();
        advertisingDto.setTargetUrl(this.targetUrl);
        advertisingDto.setTitle(this.title);
        advertisingDto.setImageDto(image.getImageDto());
        return advertisingDto;
    }

    public Long getId() {
        return id;
    }
}
