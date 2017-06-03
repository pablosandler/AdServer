package com.mycompany.adserver.enums;

public enum CampaignFilterType {

    DAY_OR_HOUR("DOH"),
    DAY("D"),
    HOUR("H"),
    REFERRER("Referer"),
    COOKIE_PARAM("C");

    private final String name;

    CampaignFilterType(String name){
        this.name=name;
    }


    @Override
    public String toString() {
        return name;
    }

}
