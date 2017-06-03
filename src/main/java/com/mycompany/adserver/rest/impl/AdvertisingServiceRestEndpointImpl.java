package com.mycompany.adserver.rest.impl;

import com.mycompany.adserver.dtos.AdvertisingDto;
import com.mycompany.adserver.dtos.CampaignFilter;
import com.mycompany.adserver.entities.Advertising;
import com.mycompany.adserver.enums.CampaignFilterType;
import com.mycompany.adserver.rest.AdvertisingServiceRestEndpoint;
import com.mycompany.adserver.services.AdvertisingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.HttpHeaders;
import java.util.*;


@Component
public class AdvertisingServiceRestEndpointImpl implements AdvertisingServiceRestEndpoint {

    @Autowired
    private AdvertisingService advertisingService;


    public AdvertisingDto get(Long id,
                              Integer dayOfTheWeek, Integer hourOfTheDay, Boolean andCondition,
                              String cookieParam, HttpHeaders httpHeaders) {

        Set<CampaignFilter> campaignFilterList = new HashSet<CampaignFilter>();

        if(cookieParam!=null){
            CampaignFilter campaignFilter = new CampaignFilter(CampaignFilterType.COOKIE_PARAM.name(),cookieParam);
            campaignFilterList.add(campaignFilter);
        }

        if(httpHeaders.getRequestHeaders().keySet().contains(CampaignFilterType.REFERRER.name())){
            CampaignFilter campaignFilter =
                    new CampaignFilter(CampaignFilterType.REFERRER.name(),
                            httpHeaders.getRequestHeaders().getFirst(CampaignFilterType.REFERRER.name()));
            campaignFilterList.add(campaignFilter);
        }

        if(dayOfTheWeek!=null || hourOfTheDay!=null){
            createDayHourFilters(dayOfTheWeek, hourOfTheDay, andCondition, campaignFilterList);
        }

        Advertising advertising = advertisingService.getAdvertisement(id, campaignFilterList);

        AdvertisingDto advertisingDto =null;
        if(advertising!=null){
            advertisingDto = advertising.getDto();
        }

        return advertisingDto;
    }



    private void createDayHourFilters(Integer dayOfTheWeek, Integer hourOfTheDay, Boolean andCondition, Set<CampaignFilter> campaignFilterList) {
        if(dayOfTheWeek!=null && !checkValidRange(dayOfTheWeek, 1, 7)){
            return;
        }
        if(hourOfTheDay!=null && !checkValidRange(hourOfTheDay, 0, 23)){
            return;
        }

        if(dayOfTheWeek!=null && hourOfTheDay!=null){
            System.out.println("day or hour");
            if(andCondition!=null && !andCondition){
                String hour = hourOfTheDay.toString();
                if(hour.length()==1){
                    hour="0"+hour;
                }

                CampaignFilter campaignFilter = new CampaignFilter(CampaignFilterType.DAY_OR_HOUR.name(),dayOfTheWeek+hour);
                campaignFilterList.add(campaignFilter);
            }
            else{
                CampaignFilter campaignFilter = new CampaignFilter(CampaignFilterType.DAY.name(),dayOfTheWeek.toString());
                campaignFilterList.add(campaignFilter);

                CampaignFilter campaignFilter2 = new CampaignFilter(CampaignFilterType.HOUR.name(),hourOfTheDay.toString());
                campaignFilterList.add(campaignFilter2);
            }
        } else {
            if(dayOfTheWeek!=null){
                CampaignFilter campaignFilter = new CampaignFilter(CampaignFilterType.DAY.name(),dayOfTheWeek.toString());
                campaignFilterList.add(campaignFilter);
            }
            if(hourOfTheDay!=null){
                CampaignFilter campaignFilter = new CampaignFilter(CampaignFilterType.HOUR.name(),hourOfTheDay.toString());
                campaignFilterList.add(campaignFilter);
            }
        }
    }

    private boolean checkValidRange(Integer value, int from, int to) {
        if(value<from || value>to){
            return false;
        }
        return true;
    }


}
