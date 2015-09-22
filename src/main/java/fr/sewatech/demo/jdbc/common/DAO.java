package fr.sewatech.demo.jdbc.common;

import java.util.List;

public interface DAO<T> {
    T findByKey(Long key);
    List<T> findByAll();
    T update(T person);
    T create(T person);
    void remove(T person);

}
