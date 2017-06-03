package com.mycompany.adserver.services.impl;

import com.mycompany.adserver.daos.AdvertisingCampaignDao;
import com.mycompany.adserver.daos.CampaignRestrictionDao;
import com.mycompany.adserver.dtos.AdvertisingCampaignDto;
import com.mycompany.adserver.dtos.CampaignFilter;
import com.mycompany.adserver.entities.AdvertisingCampaign;
import com.mycompany.adserver.entities.CampaignRestriction;
import com.mycompany.adserver.exceptions.BusinessException;
import com.mycompany.adserver.services.AdvertisingCampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class AdvertisingCampaignServiceImpl implements AdvertisingCampaignService {

    @Autowired
    private AdvertisingCampaignDao advertisingCampaignDao;

    @Autowired
    private CampaignRestrictionDao campaignRestrictionDao;


    public Long getTimesServedByCampaign(long advertisingCampaignId){
        return advertisingCampaignDao.timesServedByCampaign(advertisingCampaignId);
    }


    public void activateCampaign(long advertisingCampaignId){
        advertisingCampaignDao.activateCampaign(advertisingCampaignId);
    }

    public void deactivateCampaign(long advertisingCampaignId){
        advertisingCampaignDao.deactivateCampaign(advertisingCampaignId);
    }

    public void increaseTimesServedByCampaign(long advertisingCampaingId) {
        advertisingCampaignDao.increaseTimesServedByCampaign(advertisingCampaingId);
    }


    public AdvertisingCampaign createAdvertisingCampaign(AdvertisingCampaignDto advertisingCampaignDto) {
        AdvertisingCampaign advertisingCampaign =
                new AdvertisingCampaign(advertisingCampaignDto);
        return advertisingCampaignDao.save(advertisingCampaign);
    }

    public void addCampaignRestriction(CampaignFilter campaignFilter) {
        AdvertisingCampaign advertisingCampaign = advertisingCampaignDao.getById(campaignFilter.getCampaignId());
        if(advertisingCampaign==null){
            throw new BusinessException("Campaign doesn't exist");
        }

        CampaignRestriction campaignRestriction = new CampaignRestriction(campaignFilter.getKey(),campaignFilter.getValue(),advertisingCampaign);
        campaignRestrictionDao.save(campaignRestriction);
    }

}
