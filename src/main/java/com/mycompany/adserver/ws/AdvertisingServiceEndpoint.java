package com.mycompany.adserver.ws;

import com.mycompany.adserver.dtos.AdvertisingCampaignDto;
import com.mycompany.adserver.dtos.AdvertisingDto;
import com.mycompany.adserver.dtos.AdvertisingSpaceDto;
import com.mycompany.adserver.dtos.CampaignFilter;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface AdvertisingServiceEndpoint {

    @WebMethod
    Long createAdvertisingCampaign(AdvertisingCampaignDto advertisingDto);

    @WebMethod
    Long createAdvertising(AdvertisingDto advertisingDto);

    @WebMethod
    Long createAdvertisingSpace(AdvertisingSpaceDto advertisingSpaceDto);

    @WebMethod
    boolean activateCampaign(Long campaignId);

    @WebMethod
    boolean deactivateCampaign(Long campaignId);

    @WebMethod
    boolean createCampaignRestriction(CampaignFilter campaignFilter);
}
