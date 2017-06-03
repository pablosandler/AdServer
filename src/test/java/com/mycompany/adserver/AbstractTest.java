package com.mycompany.adserver;

import com.mycompany.adserver.daos.AdvertisingCampaignDao;
import com.mycompany.adserver.daos.AdvertisingSpaceDao;
import com.mycompany.adserver.daos.CampaignRestrictionDao;
import com.mycompany.adserver.daos.ImageDao;
import com.mycompany.adserver.entities.*;
import com.mycompany.adserver.services.AdvertisingService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public abstract class AbstractTest {

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private AdvertisingSpaceDao advertisingSpaceDao;

    @Autowired
    protected AdvertisingService advertisingService;

    @Autowired
    protected AdvertisingCampaignDao advertisingCampaignDao;

    @Autowired
    private CampaignRestrictionDao campaignRestrictionDao;

    protected AdvertisingSpace advertisingSpace;

    protected Long advertisingCampaingId;



    protected void createObjectsForTest(Long imageWidth, Long imageHeight, Integer campaignFromYear,
                                      Integer campaignToYear, boolean activeCampaign,
                                      Long campaignLimit, Long spaceWidth, Long spaceHeight,
                                      boolean campaignRestrictions) throws Exception {
        Image image = new Image("imageUrl"+imageWidth, imageWidth, imageHeight);
        imageDao.save(image);

        Advertising ad1 = new Advertising("adTitle","adUrl",image,null);

        Set ads = new HashSet<Advertising>();
        ads.add(ad1);

        Calendar calendarFrom = Calendar.getInstance();
        calendarFrom.set(campaignFromYear, 1, 1);

        Calendar calendarTo = Calendar.getInstance();
        calendarTo.set(campaignToYear, 1, 1);
        AdvertisingCampaign advertisingCampaign = new AdvertisingCampaign(activeCampaign,ads,calendarFrom.getTime(),calendarTo.getTime(),campaignLimit);

        ad1.setAdvertisingCampaign(advertisingCampaign);

        advertisingCampaign = advertisingCampaignDao.save(advertisingCampaign);
        advertisingCampaingId = advertisingCampaign.getId();

        AdvertisingSpace advertisingSpace = advertisingService.createAdvertisementSpace("a", spaceWidth, spaceHeight);

        this.advertisingSpace = advertisingSpaceDao.save(advertisingSpace);

        if(campaignRestrictions==true){
            CampaignRestriction campaignRestriction = new CampaignRestriction("key2","value2",advertisingCampaign);
            CampaignRestriction campaignRestriction2 = new CampaignRestriction("key1","value1",advertisingCampaign);
            campaignRestrictionDao.save(campaignRestriction);
            campaignRestrictionDao.save(campaignRestriction2);
        }
    }


}
