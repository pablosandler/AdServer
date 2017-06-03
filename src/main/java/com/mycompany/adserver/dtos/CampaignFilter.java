package com.mycompany.adserver.dtos;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CampaignFilter {

    private String key;
    private String value;
    private long campaignId;

    public CampaignFilter(){}

    public CampaignFilter(String key, String value) {
        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(value)){
            throw new IllegalArgumentException("Arguments must be valid Strings");
        }
        this.key = key;
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CampaignFilter that = (CampaignFilter) o;

        if (!key.equals(that.key)) return false;
        if (!value.equals(that.value)) return false;

        return true;
    }


    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    public long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(long campaignId) {
        this.campaignId = campaignId;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
