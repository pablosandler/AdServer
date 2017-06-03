package com.mycompany.adserver.daos;

import com.mycompany.adserver.entities.AdvertisingCampaign;

public interface AdvertisingCampaignDao extends EditableBaseDao<AdvertisingCampaign> {

    Long timesServedByCampaign(long advertisingCampaignId);

    void activateCampaign(long advertisingCampaignId);

    void deactivateCampaign(long advertisingCampaignId);

    void increaseTimesServedByCampaign(long advertisingCampaignId);
}
