package fr.sewatech.demo.jdbc.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import fr.sewatech.demo.jdbc.common.Person;

public interface PersonMapper extends GenericMapper<Person> {
    String FIND_ALL_QUERY = "SELECT key, name, forname FROM person ";
    String FIND_BY_KEY_QUERY = FIND_ALL_QUERY + "WHERE key=#{1} ";
    String FIND_BY_NAME_LIKE_QUERY = FIND_ALL_QUERY + "WHERE upper(name) like upper(#{1})";
    String UPDATE_QUERY = "UPDATE person SET name=#{name}, forname=#{forname} WHERE key=#{key}";
    String CREATE_QUERY = "INSERT INTO person (name, forname) VALUES (#{name}, #{forname})";
    String REMOVE_QUERY = "DELETE FROM person WHERE key=#{key}";

    @Select(FIND_BY_KEY_QUERY)
    Person findByKey(Long key);

    @Select(FIND_BY_NAME_LIKE_QUERY)
    List<Person> findByNameLike(String name);

    @Select(FIND_ALL_QUERY)
    List<Person> findAll();

    @Update(UPDATE_QUERY)
    int update(Person person);

    @Insert(CREATE_QUERY) @Options(useGeneratedKeys = true, keyProperty = "key")
    int create(Person person);

    @Update(REMOVE_QUERY)
    int remove(Person person);
}
