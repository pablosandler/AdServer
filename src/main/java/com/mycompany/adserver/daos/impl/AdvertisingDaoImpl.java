package com.mycompany.adserver.daos.impl;

import com.mycompany.adserver.daos.AbstractDao;
import com.mycompany.adserver.daos.AdvertisingDao;
import com.mycompany.adserver.dtos.CampaignFilter;
import com.mycompany.adserver.entities.Advertising;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

@Repository
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class AdvertisingDaoImpl extends AbstractDao<Advertising> implements AdvertisingDao {

    public AdvertisingDaoImpl() {
        super(Advertising.class);
    }

    public Advertising getAdvertisement(long advertisingSpaceId, Set<CampaignFilter> campaignFilters) {
        StringBuilder subqueryString = buildSubquery(campaignFilters);

        StringBuilder queryString = new StringBuilder();

        queryString.append("select ad from Advertising ad, ");
        queryString.append("Image image, ");
        queryString.append("AdvertisingSpace s, ");
        queryString.append("AdvertisingCampaign c ");
        queryString.append("left join c.campaignRestrictions as r ");
        queryString.append("where s.id=:advertisingSpaceId ");
        queryString.append("and ad.image=image ");
        queryString.append("and image.width=s.width ");
        queryString.append("and image.height=s.height ");
        queryString.append("and ad.advertisingCampaign=c ");
        queryString.append("and c.active=true ");
        queryString.append("and c.from<=:date ");
        queryString.append("and c.to>=:date ");
        queryString.append("and c.limit>c.timesServed ");
        queryString.append("and ( r is null ");
        queryString.append(" or ");
        queryString.append("( r is not null and r in ( ");
        queryString.append(subqueryString.toString());
        queryString.append(" ) ");
        queryString.append(campaignFilters!=null?"and size(c.campaignRestrictions)= "+campaignFilters.size():"");
        queryString.append(") ");
        queryString.append(") ");
        queryString.append("order by rand()");

        Advertising advertising = (Advertising)getEntityManager()
                .createQuery(queryString.toString())
                .setParameter("advertisingSpaceId", advertisingSpaceId)
                .setParameter("date", new Date())
                .setMaxResults(1)
                .getSingleResult();

        return advertising;
    }

    private StringBuilder buildSubquery(Set<CampaignFilter> campaignFilters) {
        StringBuilder subqueryString = new StringBuilder();

        if(campaignFilters!=null && campaignFilters.size()!=0){
            subqueryString.append("select cr.id from AdvertisingCampaign ac inner join ac.campaignRestrictions as cr where ac.id=c.id and ( ");

            Iterator<CampaignFilter> iterator = campaignFilters.iterator();
            while(iterator.hasNext()){
                CampaignFilter filter = iterator.next();
                subqueryString.append("(key = '");
                subqueryString.append(filter.getKey());
                subqueryString.append("' and value = '");
                subqueryString.append(filter.getValue());
                subqueryString.append(" ') ");
                if(iterator.hasNext()){
                    subqueryString.append(" or ");
                }
            }
            subqueryString.append(" ) ");
        }
        else{
            subqueryString.append("0");
        }
        return subqueryString;
    }

}
