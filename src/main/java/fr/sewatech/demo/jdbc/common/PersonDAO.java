package fr.sewatech.demo.jdbc.common;

import java.util.List;

import fr.sewatech.demo.jdbc.common.DAO;
import fr.sewatech.demo.jdbc.common.Person;

public interface PersonDAO extends DAO<Person> {
    List<Person> findByNameLike(String name);
}
