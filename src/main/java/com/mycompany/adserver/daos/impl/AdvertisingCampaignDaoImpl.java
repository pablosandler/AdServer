package com.mycompany.adserver.daos.impl;

import com.mycompany.adserver.daos.AbstractDao;
import com.mycompany.adserver.daos.AdvertisingCampaignDao;
import com.mycompany.adserver.entities.AdvertisingCampaign;
import com.mycompany.adserver.exceptions.BusinessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class AdvertisingCampaignDaoImpl  extends AbstractDao<AdvertisingCampaign> implements AdvertisingCampaignDao {

    public AdvertisingCampaignDaoImpl() {
        super(AdvertisingCampaign.class);
    }

    public Long timesServedByCampaign(long advertisingCampaignId) {
        return (Long) getEntityManager()
                .createQuery("select c.timesServed from AdvertisingCampaign c where c.id = :id")
                .setParameter("id",advertisingCampaignId).getSingleResult();
    }


    public void activateCampaign(long advertisingCampaignId){
        changeCampaignStatus(advertisingCampaignId,true);
    }


    public void deactivateCampaign(long advertisingCampaignId){
        changeCampaignStatus(advertisingCampaignId,false);
    }

    public void increaseTimesServedByCampaign(long advertisingCampaignId) {
        AdvertisingCampaign advertisingCampaign = (AdvertisingCampaign) getEntityManager()
                .createQuery("from AdvertisingCampaign c where c.id=:id")
                .setParameter("id",advertisingCampaignId).getSingleResult();

        if(advertisingCampaign==null){
            throw new BusinessException("No advertising campaign found with provided id");
        }

        advertisingCampaign.increaseTimesServed();

        update(advertisingCampaign);
    }

    private void changeCampaignStatus(long advertisingCampaignId, boolean value){
        AdvertisingCampaign advertisingCampaign = (AdvertisingCampaign) getEntityManager()
                .createQuery("from AdvertisingCampaign c where c.id=:id")
                .setParameter("id",advertisingCampaignId).getSingleResult();

        if(advertisingCampaign==null){
            throw new BusinessException("No advertising campaign found with provided id");
        }

        advertisingCampaign.setActive(value);

        update(advertisingCampaign);

    }

}
