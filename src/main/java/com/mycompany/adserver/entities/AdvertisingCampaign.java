package com.mycompany.adserver.entities;

import com.mycompany.adserver.dtos.AdvertisingCampaignDto;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "advertising_campaign")
public final class AdvertisingCampaign {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable=false)
    private final Long id;

    @Column(nullable=false)
    private  boolean active;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "advertisingCampaign", cascade = CascadeType.ALL)
    private final Set<Advertising> advertisings;

    @Column(nullable=false, name = "date_from")
    private final Date from;

    @Column(nullable=false,name = "date_to")
    private final Date to;

    @Column(nullable=false,name="service_limit")
    private final Long limit;

    @Column(nullable=false)
    private Long timesServed;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "advertisingCampaign", cascade = CascadeType.ALL)
    private final Set<CampaignRestriction> campaignRestrictions;

    public AdvertisingCampaign(boolean active, Set<Advertising> advertisings, Date from, Date to, long limit) {
        if(from==null){
            throw new IllegalArgumentException("From cannot be null");
        }

        if(to==null){
            throw new IllegalArgumentException("From cannot be null");
        }

        this.id = null;
        this.active = active;
        this.from = new Date(from.getTime());
        this.to = new Date(to.getTime());
        this.limit = limit;
        this.timesServed=0L;

        if(advertisings!=null){
            this.advertisings = new HashSet<Advertising>(advertisings);
        }
         else{
            this.advertisings=null;
        }

        campaignRestrictions=null;
    }

    public AdvertisingCampaign(AdvertisingCampaignDto advertisingCampaignDto) {
        this(advertisingCampaignDto.isActive(),null,
                advertisingCampaignDto.getFrom(),advertisingCampaignDto.getTo(),
                advertisingCampaignDto.getLimit());
    }

    public Long getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getTimesServed() {
        return timesServed;
    }

    public void increaseTimesServed(){
        this.timesServed++;
    }
}
