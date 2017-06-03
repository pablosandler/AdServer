package com.mycompany.adserver.daos;

import java.util.List;

public interface EditableBaseDao<T> {

    T save(T entity);

    T update(T entity);

    List<T> getAll();

    T getById(long id);
}
