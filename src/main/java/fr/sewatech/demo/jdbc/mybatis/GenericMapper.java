package fr.sewatech.demo.jdbc.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import fr.sewatech.demo.jdbc.common.Person;

public interface GenericMapper<E> {
    E findByKey(Long key);
    List<E> findAll();
    int update(E entity);
    int create(E entity);
    int remove(E entity);
}
