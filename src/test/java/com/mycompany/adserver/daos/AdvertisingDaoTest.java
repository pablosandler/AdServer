package com.mycompany.adserver.daos;

import com.mycompany.adserver.AbstractTest;
import com.mycompany.adserver.entities.Advertising;
import com.mycompany.adserver.entities.AdvertisingCampaign;
import com.mycompany.adserver.entities.Image;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class AdvertisingDaoTest extends AbstractTest {

    @Autowired
    AdvertisingDao advertisingDao;

    @Autowired
    private AdvertisingCampaignDao advertisingCampaignDao;


    @Test
    public void saveAdvertisement() throws Exception {
        Image image = new Image("imageUrl", 1l,1l);

        Advertising advertising = new Advertising("title","url",image,null);

        Set ads = new HashSet<Advertising>();
        ads.add(advertising);

        AdvertisingCampaign advertisingCampaign = new AdvertisingCampaign(true,ads,new Date(),new Date(),5L);

        advertising.setAdvertisingCampaign(advertisingCampaign);

        advertisingCampaignDao.save(advertisingCampaign);

        List<Advertising> a = advertisingDao.getAll();

        assertEquals(1, a.size());
    }
}
