package com.mycompany.adserver.rest;

import com.mycompany.adserver.dtos.AdvertisingDto;
import com.mycompany.adserver.dtos.CampaignFilter;
import com.mycompany.adserver.entities.Advertising;
import com.mycompany.adserver.entities.Image;
import com.mycompany.adserver.enums.CampaignFilterType;
import com.mycompany.adserver.services.AdvertisingService;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class AdvertisingServiceRestEndpointTest {

    private final static String ENDPOINT_ADDRESS = "http://localhost:8080";

    @Mock
    private AdvertisingService advertisingService;

    @InjectMocks
    @Autowired
    private AdvertisingServiceRestEndpoint advertisingServiceRestEndpoint;

    private final String IMAGE_URL = "imageUrl";
    private final String TARGET_URL = "targetUrl";
    private final String TITLE = "title";
    private final Long ADVERTISING_SPACE_ID = 1L;
    private final Long WIDTH = 1L;
    private final String PATH = "advertisingService/";

    private Image image;
    private Advertising advertising;

    private static boolean runInit = true;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        image = new Image(IMAGE_URL,WIDTH,WIDTH);
        advertising = new Advertising(TITLE,TARGET_URL,image,null);

        if(!runInit){
            return;
        }

        runInit=false;
        startServer();
    }


    private void startServer(){
        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
        sf.setServiceBean(advertisingServiceRestEndpoint);

        List<Object> providerList = new ArrayList<Object>();
        providerList.add(new org.apache.cxf.jaxrs.provider.json.JSONProvider());
        sf.setProviders(providerList);

        sf.setAddress(ENDPOINT_ADDRESS);
        sf.create();
    }



    @Test
    public void testGetAdvertisingJSON() throws Exception {
        when(advertisingService.getAdvertisement(argThat(Matchers.is(ADVERTISING_SPACE_ID)), (Set<CampaignFilter>) argThat(Matchers.hasSize(0)))).thenReturn(advertising);

        WebClient client = WebClient.create(ENDPOINT_ADDRESS);
        client.accept(MediaType.APPLICATION_JSON);

        client.path(PATH+ADVERTISING_SPACE_ID);
        AdvertisingDto returnedAdvertisingDto = client.get(AdvertisingDto.class);
        assertNotNull(returnedAdvertisingDto);
        assertEquals(advertising.getDto(), returnedAdvertisingDto);
    }


    @Test
    public void testGetAdvertisingXML() throws Exception {
        when(advertisingService.getAdvertisement(argThat(Matchers.is(ADVERTISING_SPACE_ID)), (Set<CampaignFilter>) argThat(Matchers.hasSize(0)))).thenReturn(advertising);

        WebClient client = WebClient.create(ENDPOINT_ADDRESS);
        client.accept(MediaType.APPLICATION_XML);

        client.path(PATH+ADVERTISING_SPACE_ID);
        AdvertisingDto returnedAdvertisingDto = client.get(AdvertisingDto.class);
        assertNotNull(returnedAdvertisingDto);
        assertEquals(advertising.getDto(), returnedAdvertisingDto);
    }


    @Test
    public void testGetAdvertisingPlainTest(){
        WebClient client = WebClient.create(ENDPOINT_ADDRESS);
        client.accept(MediaType.TEXT_PLAIN);

        client.path(PATH+ADVERTISING_SPACE_ID);
        try{
            client.get(AdvertisingDto.class);
            fail();
        } catch (NotAcceptableException e){
              assertNotNull(e);
        }
    }


    @Test
    public void testGetAdvertisingWithCookieFilter() throws Exception {
        final String COOKIE_VALUE ="123456";
        CampaignFilter campaignFilter = new CampaignFilter(CampaignFilterType.COOKIE_PARAM.name(),COOKIE_VALUE);
        when(advertisingService.getAdvertisement(argThat(Matchers.is(ADVERTISING_SPACE_ID)), (Set<CampaignFilter>) argThat(Matchers.contains(campaignFilter)))).thenReturn(advertising);

        WebClient client = WebClient.create(ENDPOINT_ADDRESS);
        client.accept(MediaType.APPLICATION_XML);

        Cookie cookie = new Cookie("cookieParam",COOKIE_VALUE);
        client.cookie(cookie);

        client.path(PATH+ADVERTISING_SPACE_ID);
        AdvertisingDto returnedAdvertisingDto = client.get(AdvertisingDto.class);
        assertNotNull(returnedAdvertisingDto);
        assertEquals(advertising.getDto(), returnedAdvertisingDto);
    }


    @Test
    public void testGetAdvertisingWithHTTPReferrerFilter() throws Exception {
        final String REFERRER_VALUE = "referrerValue";
        CampaignFilter campaignFilter = new CampaignFilter(CampaignFilterType.REFERRER.name(),REFERRER_VALUE);
        when(advertisingService.getAdvertisement(argThat(Matchers.is(ADVERTISING_SPACE_ID)), (Set<CampaignFilter>) argThat(Matchers.contains(campaignFilter)))).thenReturn(advertising);

        WebClient client = WebClient.create(ENDPOINT_ADDRESS);
        client.accept(MediaType.APPLICATION_XML);

        client.header("Referrer",REFERRER_VALUE);

        client.path(PATH+ADVERTISING_SPACE_ID);
        AdvertisingDto returnedAdvertisingDto = client.get(AdvertisingDto.class);
        assertNotNull(returnedAdvertisingDto);
        assertEquals(advertising.getDto(), returnedAdvertisingDto);
    }


    @Test
    public void testGetAdvertisingWithDayOfTheWeekFilter() throws Exception {
        final Integer DAY_VALUE = 2;
        CampaignFilter campaignFilter = new CampaignFilter(CampaignFilterType.DAY.name(),DAY_VALUE.toString());
        when(advertisingService.getAdvertisement(argThat(Matchers.is(ADVERTISING_SPACE_ID)), (Set<CampaignFilter>) argThat(Matchers.contains(campaignFilter)))).thenReturn(advertising);

        WebClient client = WebClient.create(ENDPOINT_ADDRESS);
        client.accept(MediaType.APPLICATION_XML);

        client.query("day",DAY_VALUE);

        client.path(PATH+ADVERTISING_SPACE_ID);
        AdvertisingDto returnedAdvertisingDto = client.get(AdvertisingDto.class);

        assertNotNull(returnedAdvertisingDto);
    }


    @Test
    public void testGetAdvertisingWithHourOfTheDayFilter() throws Exception {
        final Integer HOUR_VALUE = 22;
        CampaignFilter campaignFilter = new CampaignFilter(CampaignFilterType.HOUR.name(),HOUR_VALUE.toString());
        when(advertisingService.getAdvertisement(argThat(Matchers.is(ADVERTISING_SPACE_ID)), (Set<CampaignFilter>) argThat(Matchers.contains(campaignFilter)))).thenReturn(advertising);

        WebClient client = WebClient.create(ENDPOINT_ADDRESS);
        client.accept(MediaType.APPLICATION_XML);

        client.query("hour",HOUR_VALUE);

        client.path(PATH+ADVERTISING_SPACE_ID);
        AdvertisingDto returnedAdvertisingDto = client.get(AdvertisingDto.class);

        assertNotNull(returnedAdvertisingDto);
    }



   @Test
    public void testGetAdvertisingWithHourOfTheDayAndDayOfTheWeekFilter() throws Exception {
       final Integer DAY_VALUE = 2;
       final Integer HOUR_VALUE = 22;
       CampaignFilter campaignFilter = new CampaignFilter(CampaignFilterType.DAY.name(),DAY_VALUE.toString());
       CampaignFilter campaignFilter2 = new CampaignFilter(CampaignFilterType.HOUR.name(),HOUR_VALUE.toString());

       when(advertisingService.getAdvertisement(argThat(Matchers.is(ADVERTISING_SPACE_ID)), (Set<CampaignFilter>) argThat(Matchers.contains(campaignFilter,campaignFilter2)))).thenReturn(advertising);

        WebClient client = WebClient.create(ENDPOINT_ADDRESS);
        client.accept(MediaType.APPLICATION_XML);

        client.query("day",DAY_VALUE);
        client.query("hour",HOUR_VALUE);

        client.path(PATH+ADVERTISING_SPACE_ID);
        AdvertisingDto returnedAdvertisingDto = client.get(AdvertisingDto.class);

        assertNotNull(returnedAdvertisingDto);
    }


    @Test
    public void testGetAdvertisingWithHourOfTheDayAndDayOfTheWeekAndAndConditionTrueFilter() throws Exception {
        final Integer DAY_VALUE = 2;
        final Integer HOUR_VALUE = 22;
        CampaignFilter campaignFilter = new CampaignFilter(CampaignFilterType.DAY.name(),DAY_VALUE.toString());
        CampaignFilter campaignFilter2 = new CampaignFilter(CampaignFilterType.HOUR.name(),HOUR_VALUE.toString());

        when(advertisingService.getAdvertisement(argThat(Matchers.is(ADVERTISING_SPACE_ID)), (Set<CampaignFilter>) argThat(Matchers.contains(campaignFilter,campaignFilter2)))).thenReturn(advertising);

        WebClient client = WebClient.create(ENDPOINT_ADDRESS);
        client.accept(MediaType.APPLICATION_XML);

        client.query("day",DAY_VALUE);
        client.query("hour",HOUR_VALUE);
        client.query("andCondition",true);

        client.path(PATH+ADVERTISING_SPACE_ID);
        AdvertisingDto returnedAdvertisingDto = client.get(AdvertisingDto.class);

        assertNotNull(returnedAdvertisingDto);
    }

    @Test
    public void testGetAdvertisingWithHourOfTheDayOrDayOfTheWeekFilter() throws Exception {
        final Integer DAY_VALUE = 2;
        final Integer HOUR_VALUE = 22;
        CampaignFilter campaignFilter = new CampaignFilter(CampaignFilterType.DAY_OR_HOUR.name(),DAY_VALUE.toString()+HOUR_VALUE.toString());
        when(advertisingService.getAdvertisement(argThat(Matchers.is(ADVERTISING_SPACE_ID)), (Set<CampaignFilter>) argThat(Matchers.contains(campaignFilter)))).thenReturn(advertising);

        WebClient client = WebClient.create(ENDPOINT_ADDRESS);
        client.accept(MediaType.APPLICATION_XML);

        client.query("day",DAY_VALUE);
        client.query("hour",HOUR_VALUE);
        client.query("andCondition",false);

        client.path(PATH+ADVERTISING_SPACE_ID);
        AdvertisingDto returnedAdvertisingDto = client.get(AdvertisingDto.class);

        assertNotNull(returnedAdvertisingDto);
    }

}
