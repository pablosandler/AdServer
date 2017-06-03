package com.mycompany.adserver.services;

import com.mycompany.adserver.dtos.AdvertisingCampaignDto;
import com.mycompany.adserver.dtos.CampaignFilter;
import com.mycompany.adserver.entities.AdvertisingCampaign;

public interface AdvertisingCampaignService {

    Long getTimesServedByCampaign(long advertisingCampaignId);

    void activateCampaign(long advertisingCampaignId);

    void deactivateCampaign(long advertisingCampaignId);

    void increaseTimesServedByCampaign(long advertisingCampaingId);

    AdvertisingCampaign createAdvertisingCampaign(AdvertisingCampaignDto advertisingCampaignDto);

    void addCampaignRestriction(CampaignFilter campaignFilter);
}
