package com.mycompany.adserver.services.impl;

import com.mycompany.adserver.daos.AdvertisingCampaignDao;
import com.mycompany.adserver.daos.AdvertisingDao;
import com.mycompany.adserver.daos.AdvertisingSpaceDao;
import com.mycompany.adserver.dtos.AdvertisingDto;
import com.mycompany.adserver.dtos.CampaignFilter;
import com.mycompany.adserver.entities.Advertising;
import com.mycompany.adserver.entities.AdvertisingCampaign;
import com.mycompany.adserver.entities.AdvertisingSpace;
import com.mycompany.adserver.exceptions.BusinessException;
import com.mycompany.adserver.services.AdvertisingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class AdvertisingServiceImpl implements AdvertisingService {

    @Autowired
    private AdvertisingSpaceDao advertisingSpaceDao;

    @Autowired
    private AdvertisingDao advertisingDao;

    @Autowired
    private AdvertisingCampaignDao advertisingCampaignDao;


    public Advertising getAdvertisement(Long advertisingSpaceId, Set<CampaignFilter> campaignFilters){
        if(advertisingSpaceId==null || advertisingSpaceId<=0){
            return null;
        }

        AdvertisingSpace advertisingSpace = advertisingSpaceDao.getById(advertisingSpaceId);

        if(advertisingSpace==null){
            return null;
        }

        Advertising advertising = advertisingDao.getAdvertisement(advertisingSpaceId, campaignFilters);

        if(advertising!=null){
            advertisingCampaignDao.increaseTimesServedByCampaign(advertising.getAdvertisingCampaign().getId());
        }

        return advertising;
    }



    public AdvertisingSpace createAdvertisementSpace(String website, long width, long height) throws Exception {
        AdvertisingSpace advertisingSpace = new AdvertisingSpace(website,width,height);
        advertisingSpaceDao.save(advertisingSpace);

        return advertisingSpace;
    }

    @Override
    public Advertising createAdvertising(AdvertisingDto advertisingDto) {
        if(advertisingDto.getAdvertisingCampaignDto()==null || advertisingDto.getAdvertisingCampaignDto().getId()==null){
            throw new BusinessException("Campaign cannot be null");
        }

        AdvertisingCampaign advertisingCampaign = advertisingCampaignDao.getById(advertisingDto.getAdvertisingCampaignDto().getId());
        if(advertisingCampaign==null){
            throw new BusinessException("Campaign not found");
        }
        Advertising advertising = new Advertising(advertisingDto,advertisingCampaign);
        return advertisingDao.save(advertising);
    }

}
