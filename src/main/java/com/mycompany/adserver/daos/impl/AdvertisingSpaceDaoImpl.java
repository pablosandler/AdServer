package com.mycompany.adserver.daos.impl;

import com.mycompany.adserver.daos.AbstractDao;
import com.mycompany.adserver.daos.AdvertisingSpaceDao;
import com.mycompany.adserver.entities.AdvertisingSpace;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class AdvertisingSpaceDaoImpl extends AbstractDao<AdvertisingSpace> implements AdvertisingSpaceDao {


    public AdvertisingSpaceDaoImpl() {
        super(AdvertisingSpace.class);
    }

}
