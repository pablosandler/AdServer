package com.mycompany.adserver.entities;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Entity
@Table(name = "campaign_restriction")
public final class CampaignRestriction {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable=false)
    private final Long id;

    @Column(nullable=false)
    private final String key;

    @Column(nullable=false)
    private final String value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="campaign_id")
    private final AdvertisingCampaign advertisingCampaign;


    public CampaignRestriction(String key, String value, AdvertisingCampaign advertisingCampaign) {
        if(StringUtils.isEmpty(key)){
            throw new IllegalArgumentException("Key cannot be null");
        }

        if(StringUtils.isEmpty(value)){
            throw new IllegalArgumentException("Value cannot be null");
        }

        if(advertisingCampaign==null){
            throw new IllegalArgumentException("Campaign cannot be null");
        }

        this.id=null;
        this.key = key;
        this.value=value;
        this.advertisingCampaign=advertisingCampaign;
    }

}
