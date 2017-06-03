package com.mycompany.adserver.services;

import com.mycompany.adserver.AbstractTest;
import com.mycompany.adserver.dtos.CampaignFilter;
import com.mycompany.adserver.entities.AdvertisingCampaign;
import com.mycompany.adserver.exceptions.BusinessException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;

public class AdvertisingCampaignServiceTest extends AbstractTest {


    @Autowired
    private AdvertisingCampaignService advertisingCampaignService;


    @Test
    public void testGetTimesServedByCampaignWithExistingCampaign() throws Exception {
        createObjectsForTest(1L, 1L, 1200, 2900, true, 1L, 1L, 1L, false);
        Long times = advertisingCampaignService.getTimesServedByCampaign(advertisingCampaingId);
        assertEquals(new Long(0L),times);
    }

    @Test
    public void testGetTimesServedByCampaignWithNonexistingCampaign(){
        Long times = advertisingCampaignService.getTimesServedByCampaign(100L);
        assertNull(times);
    }

    @Test
    public void testActivateExistingCampaign() throws Exception {
        createObjectsForTest(1L, 1L, 1200, 2900, false, 1L, 1L, 1L, false);

        AdvertisingCampaign advertisingCampaign = advertisingCampaignDao.getById(advertisingCampaingId);
        assertFalse(advertisingCampaign.isActive());

        advertisingCampaignService.activateCampaign(advertisingCampaingId);

        advertisingCampaign = advertisingCampaignDao.getById(advertisingCampaingId);
        assertTrue(advertisingCampaign.isActive());
    }

    @Test
    public void testActivateNonexistingCampaign() throws Exception {
        try{
            advertisingCampaignService.activateCampaign(500L);
            fail();
        } catch(Exception e){
            assertNotNull(e);
        }
    }

    @Test
    public void testDeactivateExistingCampaign() throws Exception {
        createObjectsForTest(1L, 1L, 1200, 2900, true, 1L, 1L, 1L,false);

        AdvertisingCampaign advertisingCampaign = advertisingCampaignDao.getById(advertisingCampaingId);
        assertTrue(advertisingCampaign.isActive());

        advertisingCampaignService.deactivateCampaign(advertisingCampaingId);

        advertisingCampaign = advertisingCampaignDao.getById(advertisingCampaingId);
        assertFalse(advertisingCampaign.isActive());
    }

    @Test
    public void testDeactivateNonexistingCampaign(){
        try{
            advertisingCampaignService.deactivateCampaign(500L);
            fail();
        } catch(Exception e){
            assertNotNull(e);
        }
    }

    @Test
    public void testIncreaseTimesServedByCampaign() throws Exception {
        createObjectsForTest(1L, 1L, 1200, 2900, true, 1L, 1L, 1L,false);

        AdvertisingCampaign advertisingCampaign = advertisingCampaignDao.getById(advertisingCampaingId);
        assertEquals(0L, advertisingCampaign.getTimesServed().longValue());

        advertisingCampaignService.increaseTimesServedByCampaign(advertisingCampaingId);

        advertisingCampaign = advertisingCampaignDao.getById(advertisingCampaingId);
        assertEquals(1l, advertisingCampaign.getTimesServed().longValue());
    }


    @Test
    public void testAddCampaignRestriction() throws Exception {
        createObjectsForTest(1L, 1L, 1200, 2900, true, 1L, 1L, 1L, false);

        CampaignFilter campaignRestriction = new CampaignFilter("key","value");
        campaignRestriction.setCampaignId(advertisingCampaingId);
        advertisingCampaignService.addCampaignRestriction(campaignRestriction);
    }

    @Test
    public void testAddCampaignRestrictionWithNonExistingCampaign() throws Exception {
        CampaignFilter campaignRestriction = new CampaignFilter("key","value");
        campaignRestriction.setCampaignId(100L);
        try{
            advertisingCampaignService.addCampaignRestriction(campaignRestriction);
            fail();
        } catch (BusinessException e){
            assertNotNull(e);
        }

    }


}
