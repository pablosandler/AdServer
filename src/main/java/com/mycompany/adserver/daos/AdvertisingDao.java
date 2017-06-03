package com.mycompany.adserver.daos;

import com.mycompany.adserver.dtos.CampaignFilter;
import com.mycompany.adserver.entities.Advertising;

import java.util.Set;

public interface AdvertisingDao extends EditableBaseDao<Advertising> {

    Advertising getAdvertisement(long advertisingSpaceId, Set<CampaignFilter> campaignFilters);

}
