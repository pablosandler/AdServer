package com.mycompany.adserver.entities;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Entity
@Table(name = "advertising_space")
public final class AdvertisingSpace {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable=false)
    private final Long id;

    @Column(nullable=false)
    private final String website;

    @Column(nullable=false)
    private final Long width;

    @Column(nullable=false)
    private final Long height;

    public Long getId() {
        return id;
    }


    public AdvertisingSpace(String website, long width, long height) throws IllegalArgumentException {

        if(StringUtils.isEmpty(website)){
            throw new IllegalArgumentException("Empty website");
        }

        if(width<=0 || height<=0){
            throw new IllegalArgumentException("Width and height must be natural numbers");
        }

        this.id = null;
        this.website = website;
        this.width = width;
        this.height = height;
    }

}
