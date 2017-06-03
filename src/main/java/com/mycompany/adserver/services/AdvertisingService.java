package com.mycompany.adserver.services;

import com.mycompany.adserver.dtos.AdvertisingDto;
import com.mycompany.adserver.dtos.CampaignFilter;
import com.mycompany.adserver.entities.Advertising;
import com.mycompany.adserver.entities.AdvertisingSpace;

import java.util.Set;

public interface AdvertisingService {

    Advertising getAdvertisement(Long advertisementSpace, Set<CampaignFilter> campaignFilters);

    AdvertisingSpace createAdvertisementSpace(String website, long width, long height) throws Exception;

    Advertising createAdvertising(AdvertisingDto advertisingDto);
}
