package com.alvino.mavappextintor.bancodados;

import java.util.List;

/**
 * Created by alvino on 21/08/15.
 */
interface CRUD<T> {
    public long insert(T t);
    public int upgrade(T t);
    public int delete(T t);
    public List<T> all();
    public T get(long id);
}
