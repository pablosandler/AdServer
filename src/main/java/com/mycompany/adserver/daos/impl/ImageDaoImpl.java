package com.mycompany.adserver.daos.impl;

import com.mycompany.adserver.daos.AbstractDao;
import com.mycompany.adserver.daos.ImageDao;
import com.mycompany.adserver.entities.Image;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ImageDaoImpl extends AbstractDao<Image> implements ImageDao {
    private SessionFactory sessionFactory;

    public ImageDaoImpl() {
        super(Image.class);
    }

}
