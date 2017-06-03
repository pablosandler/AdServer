package com.mycompany.adserver.ws;

import com.mycompany.adserver.dtos.*;
import com.mycompany.adserver.entities.Advertising;
import com.mycompany.adserver.entities.AdvertisingCampaign;
import com.mycompany.adserver.entities.AdvertisingSpace;
import com.mycompany.adserver.entities.Image;
import com.mycompany.adserver.services.AdvertisingCampaignService;
import com.mycompany.adserver.services.AdvertisingService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class AdvertisingServiceEndpointTest {

    private final static String ENDPOINT_ADDRESS = "http://localhost:9080";

    @Autowired
    @InjectMocks
    private AdvertisingServiceEndpoint advertisingServiceEndpoint;

    @Mock
    private AdvertisingCampaignService advertisingCampaignService;

    @Mock
    private AdvertisingService advertisingService;

    private static boolean runInit=true;

    private AdvertisingServiceEndpoint client;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        if(!runInit){
            return;
        }

        runInit=false;
        buildServer();
    }

    private void buildServer() {
        JaxWsServerFactoryBean sf = new JaxWsServerFactoryBean();

        sf.setServiceClass(AdvertisingServiceEndpoint.class);
        sf.setAddress(ENDPOINT_ADDRESS);
        sf.setServiceBean(advertisingServiceEndpoint);

        Endpoint e = sf.create().getEndpoint();
        Map<String,Object> inProps=new HashMap<String,Object>();
        inProps.put(WSHandlerConstants.ACTION,WSHandlerConstants.USERNAME_TOKEN);
        inProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
        inProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,"ClientPasswordCallback");
        WSS4JInInterceptor wss4j =new WSS4JInInterceptor(inProps);
        e.getInInterceptors().add(wss4j);
    }


    private void buildClient() {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(AdvertisingServiceEndpoint.class);
        factory.setAddress(ENDPOINT_ADDRESS+"/advertisingServiceEndpoint");

        client = (AdvertisingServiceEndpoint) factory.create();

        Client clientProxy = ClientProxy.getClient(client);
        Endpoint endpoint = clientProxy.getEndpoint();
        Map<String,Object> props = new HashMap<String,Object>();
        props.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        props.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
        props.put(WSHandlerConstants.USER, "admin");

        WSS4JOutInterceptor wss4JOutInterceptor = new WSS4JOutInterceptor(props);
        endpoint.getOutInterceptors().add(wss4JOutInterceptor);

        Map<String, Object> ctx = ((BindingProvider) client).getRequestContext();
        ctx.put("password", "admin");
    }

    @Test
    public void testSecurityWithInvalidPassword(){
        buildClient();
        Map<String, Object> ctx = ((BindingProvider) client).getRequestContext();
        ctx.put("password", "123");

        try{
            client.createAdvertisingCampaign(new AdvertisingCampaignDto());
            fail();
        } catch (SOAPFaultException e){
            assertNotNull(e);
        }
    }


    @Test
    public void testCreateAdvertisingCampaign(){
        buildClient();
        Long advertisingCampaignId = 1L;

        AdvertisingCampaign advertisingCampaign = new AdvertisingCampaign(true,null,new Date(),new Date(),1);
        ReflectionTestUtils.setField(advertisingCampaign, "id", advertisingCampaignId);

        AdvertisingCampaignDto advertisingCampaignDto = new AdvertisingCampaignDto();
        advertisingCampaignDto.setActive(true);
        advertisingCampaignDto.setFrom(new Date());
        advertisingCampaignDto.setTo(new Date());
        advertisingCampaignDto.setLimit(5L);

        when(advertisingCampaignService.createAdvertisingCampaign(any(AdvertisingCampaignDto.class))).thenReturn(advertisingCampaign);

        Long id = client.createAdvertisingCampaign(advertisingCampaignDto);
        assertEquals(advertisingCampaignId,id);
    }



    @Test
    public void testCreateAdvertising(){
        buildClient();

        Long advertisingId = 1L;

        Advertising advertising = new Advertising("title","url",new Image("s",1,1),null);
        ReflectionTestUtils.setField(advertising, "id", advertisingId);

        when(advertisingService.createAdvertising(any(AdvertisingDto.class))).thenReturn(advertising);

        ImageDto imageDto = new ImageDto();
        imageDto.setUrl("url");
        imageDto.setHeight(1l);
        imageDto.setWidth(1l);

        AdvertisingCampaignDto advertisingCampaignDto = new AdvertisingCampaignDto();
        advertisingCampaignDto.setId(1L);

        AdvertisingDto advertisingDto = new AdvertisingDto();
        advertisingDto.setImageDto(imageDto);
        advertisingDto.setTargetUrl("targetUrl");
        advertisingDto.setTitle("title");
        advertisingDto.setAdvertisingCampaignDto(advertisingCampaignDto);

        Long id = client.createAdvertising(advertisingDto);
        assertEquals(advertisingId,id);
    }


    @Test
    public void testCreateAdvertisingSpace(){
        buildClient();

        Long advertisingSpaceId = 1L;
        AdvertisingSpace advertisingSpace = new AdvertisingSpace("website",1,1);
        ReflectionTestUtils.setField(advertisingSpace, "id", advertisingSpaceId);


        AdvertisingSpaceDto advertisingSpaceDto = new AdvertisingSpaceDto();
        advertisingSpaceDto.setHeight(1);
        advertisingSpaceDto.setWidth(1);
        advertisingSpaceDto.setWebsite("website");

        try {
            when(advertisingService.createAdvertisementSpace("website",1,1)).thenReturn(advertisingSpace);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Long id = client.createAdvertisingSpace(advertisingSpaceDto);
        assertEquals(advertisingSpaceId,id);
    }


    @Test
    public void testActivateCampaign(){
        buildClient();
        Long campaignId = 1L;

        boolean value = client.activateCampaign(campaignId);
        assertTrue(value);
    }


    @Test
    public void testDeactivateCampaign(){
        buildClient();
        Long campaignId = 1L;

        boolean value = client.deactivateCampaign(campaignId);
        assertTrue(value);
    }


    @Test
    public void testCreateCampaignRestriction(){
        buildClient();

        CampaignFilter campaignFilter = new CampaignFilter("key","value");
        campaignFilter.setCampaignId(1L);

        boolean value = client.createCampaignRestriction(campaignFilter);
        assertTrue(value);
    }


}
