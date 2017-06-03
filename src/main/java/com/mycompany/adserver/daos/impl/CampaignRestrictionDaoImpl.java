package com.mycompany.adserver.daos.impl;

import com.mycompany.adserver.daos.AbstractDao;
import com.mycompany.adserver.daos.CampaignRestrictionDao;
import com.mycompany.adserver.entities.CampaignRestriction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class CampaignRestrictionDaoImpl extends AbstractDao<CampaignRestriction> implements CampaignRestrictionDao {

    public CampaignRestrictionDaoImpl() {
        super(CampaignRestriction.class);
    }

}
