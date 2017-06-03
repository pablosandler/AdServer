package com.mycompany.adserver.ws.impl;

import com.mycompany.adserver.dtos.AdvertisingCampaignDto;
import com.mycompany.adserver.dtos.AdvertisingDto;
import com.mycompany.adserver.dtos.AdvertisingSpaceDto;
import com.mycompany.adserver.dtos.CampaignFilter;
import com.mycompany.adserver.entities.Advertising;
import com.mycompany.adserver.entities.AdvertisingCampaign;
import com.mycompany.adserver.entities.AdvertisingSpace;
import com.mycompany.adserver.exceptions.BusinessException;
import com.mycompany.adserver.services.AdvertisingCampaignService;
import com.mycompany.adserver.services.AdvertisingService;
import com.mycompany.adserver.ws.AdvertisingServiceEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

@WebService(
        endpointInterface = "com.mycompany.adserver.ws.AdvertisingServiceEndpoint",
        serviceName="advertisingServiceEndpoint")
@Service
public class AdvertisingServiceEndpointImpl implements AdvertisingServiceEndpoint {

    @Autowired
    private AdvertisingCampaignService advertisingCampaignService;

    @Autowired
    private AdvertisingService advertisingService;

    public Long createAdvertisingCampaign(AdvertisingCampaignDto advertisingDto) {
        AdvertisingCampaign advertisingCampaign = advertisingCampaignService.createAdvertisingCampaign(advertisingDto);
        return advertisingCampaign.getId();
    }


    public Long createAdvertising(AdvertisingDto advertisingDto) {
        Advertising advertising = advertisingService.createAdvertising(advertisingDto);
        return advertising.getId();
    }


    public Long createAdvertisingSpace(AdvertisingSpaceDto advertisingSpaceDto) {
        try {
            AdvertisingSpace advertisingSpace = advertisingService.createAdvertisementSpace(advertisingSpaceDto.getWebsite(), advertisingSpaceDto.getWidth(), advertisingSpaceDto.getHeight());
            return advertisingSpace.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean activateCampaign(Long campaignId) {
        if(campaignId==null){
            throw new BusinessException("Campaign id is null");
        }

        try{
            advertisingCampaignService.activateCampaign(campaignId);
        } catch (BusinessException e){
            return false;
        }
        return true;
    }


    public boolean deactivateCampaign(Long campaignId) {
        if(campaignId==null){
            throw new BusinessException("Campaign id is null");
        }

        try{
            advertisingCampaignService.deactivateCampaign(campaignId);
        } catch (BusinessException e){
            return false;
        }
        return true;
    }

    public boolean createCampaignRestriction(CampaignFilter campaignFilter) {
        try{
            advertisingCampaignService.addCampaignRestriction(campaignFilter);
            return true;
        } catch (BusinessException e){
            return false;
        }

    }

}
