package com.mycompany.adserver.services;

import com.mycompany.adserver.AbstractTest;
import com.mycompany.adserver.dtos.AdvertisingCampaignDto;
import com.mycompany.adserver.dtos.AdvertisingDto;
import com.mycompany.adserver.dtos.CampaignFilter;
import com.mycompany.adserver.dtos.ImageDto;
import com.mycompany.adserver.exceptions.BusinessException;
import com.mycompany.adserver.entities.Advertising;
import com.mycompany.adserver.entities.AdvertisingSpace;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.fail;

public class AdvertisingServiceTest extends AbstractTest {

    @Test
    public void testGetAdvertisement() throws Exception {
        createObjectsForTest(1L, 1L, 1200, 2900, true, 5L, 1L, 1L, false);
        Advertising ad = advertisingService.getAdvertisement(advertisingSpace.getId(),null);

        assertNotNull(ad);
    }

    @Test
    public void testGetAdvertisementGettingNoResultsDueToNonexistentAdvertisingSpace() throws Exception {
        Advertising ad = advertisingService.getAdvertisement(100L,null);
        assertNull(ad);
    }

    @Test
    public void testGetAdvertisementGettingNoResultsDueToNoImagesMatchingAdvertisingSpace() throws Exception {
        createObjectsForTest(1L, 1L, 1200, 2900, true, 5L, 2L, 2L, false);
        Advertising ad = advertisingService.getAdvertisement(advertisingSpace.getId(),null);
        assertNull(ad);
    }

    @Test
    public void testGetAdvertisementGettingNoResultsDueToInactiveCampaign() throws Exception {
        createObjectsForTest(1L, 1L, 1200, 2900, false, 5L, 1L, 1L, false);
        Advertising ad = advertisingService.getAdvertisement(advertisingSpace.getId(), null);
        assertNull(ad);
    }

    @Test
    public void testGetAdvertisementGettingNoResultsDueToRuntimeExpiration() throws Exception {
        createObjectsForTest(1L, 1L, 1200, 2000, true, 5L, 1L, 1L, false);
        Advertising ad = advertisingService.getAdvertisement(advertisingSpace.getId(), null);
        assertNull(ad);
    }

    @Test
    public void testGetAdvertisementGettingNoResultsDueToLimitReached() throws Exception {
        createObjectsForTest(1L, 1L, 1200, 2900, true, 0L, 1L, 1L, false);
        Advertising ad = advertisingService.getAdvertisement(advertisingSpace.getId(),null);
        assertNull(ad);
    }

    @Test
    public void testGetAdvertisementWithFiltersAttachedToCampaignAndNoFiltersSend() throws Exception {
        createObjectsForTest(1L, 1L, 1200, 2900, true, 5L, 1L, 1L, true);

        Advertising ad = advertisingService.getAdvertisement(advertisingSpace.getId(),null);

        assertNull(ad);
    }

    @Test
    public void testGetAdvertisementWithFiltersAttachedToCampaignAndAllMatching() throws Exception {
        createObjectsForTest(1L, 1L, 1200, 2900, true, 5L, 1L, 1L, true);

        Set<CampaignFilter> campaignFilters = new HashSet<CampaignFilter>();

        CampaignFilter campaignFilter1 = new CampaignFilter("key1","value1");

        CampaignFilter campaignFilter2 = new CampaignFilter("key2","value2");

        campaignFilters.add(campaignFilter1);
        campaignFilters.add(campaignFilter2);

        Advertising ad = advertisingService.getAdvertisement(advertisingSpace.getId(),campaignFilters);

        assertNotNull(ad);
    }

    @Test
    public void testGetAdvertisementWithFiltersAttachedToCampaignAndOnlyOneMatching() throws Exception {
        createObjectsForTest(1L, 1L, 1200, 2900, true, 5L, 1L, 1L, true);

        Set<CampaignFilter> campaignFilters = new HashSet<CampaignFilter>();

        CampaignFilter campaignFilter1 = new CampaignFilter("key1","value1");

        campaignFilters.add(campaignFilter1);

        Advertising ad = advertisingService.getAdvertisement(advertisingSpace.getId(),campaignFilters);

        assertNull(ad);
    }



    /////// ADVERTISING SPACE TESTS ///////
    @Test
    public void testCreateAdvertisingSpaceSuccessfully() throws Exception {
        AdvertisingSpace advertisingSpace = advertisingService.createAdvertisementSpace("asd", 1L, 1L);
        Assert.assertNotNull(advertisingSpace);
    }


    @Test
    public void testCreateAdvertisingSpaceWithEmptyWebsite() throws Exception {
        AdvertisingSpace advertisingSpace = null;

        try{
            advertisingService.createAdvertisementSpace("", 1L, 1L);
            fail();
        } catch(Exception e){
            assertNotNull(e);
            assertNull(advertisingSpace);
        }

    }

    @Test
    public void testCreateAdvertisingSpaceWithNegativeWidth() throws Exception {
        AdvertisingSpace advertisingSpace = null;

        try{
            advertisingSpace = advertisingService.createAdvertisementSpace("asd", -1L, 1L);
            fail();
        } catch(Exception e){
            assertNotNull(e);
            assertNull(advertisingSpace);
        }

    }



    @Test
    public void testCreateAdvertisingSpaceWithNegativeHeight() throws Exception {
        AdvertisingSpace advertisingSpace = null;

        try{
            advertisingService.createAdvertisementSpace("asd", 1L, -11L);
            fail();
        } catch(Exception e){
            assertNotNull(e);
            assertNull(advertisingSpace);
        }
    }

    @Test
    public void testCreateAdvertising() throws Exception {
        createObjectsForTest(1L, 1L, 1200, 2900, true, 5L, 1L, 1L, false);
        ImageDto imageDto = new ImageDto();
        imageDto.setUrl("url");
        imageDto.setHeight(1l);
        imageDto.setWidth(1l);

        AdvertisingCampaignDto advertisingCampaignDto = new AdvertisingCampaignDto();
        advertisingCampaignDto.setId(advertisingCampaingId);

        AdvertisingDto advertisingDto = new AdvertisingDto();
        advertisingDto.setImageDto(imageDto);
        advertisingDto.setTargetUrl("targetUrl");
        advertisingDto.setTitle("title");
        advertisingDto.setAdvertisingCampaignDto(advertisingCampaignDto);

        Advertising advertising = advertisingService.createAdvertising(advertisingDto);
        assertNotNull(advertising);
    }


    @Test
    public void testCreateAdvertisingWithNullCampaign() throws Exception {

        AdvertisingDto advertisingDto = new AdvertisingDto();
        advertisingDto.setImageDto(null);
        advertisingDto.setTargetUrl("targetUrl");
        advertisingDto.setTitle("title");
        advertisingDto.setAdvertisingCampaignDto(null);

        try{
            advertisingService.createAdvertising(advertisingDto);
            fail();
        } catch (BusinessException e){
            Assert.assertNotNull(e);
        }
    }


}
