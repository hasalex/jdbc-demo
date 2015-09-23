package fr.sewatech.demo.jdbc.mybatis;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;

import fr.sewatech.demo.jdbc.common.DAOException;
import fr.sewatech.demo.jdbc.common.Person;
import fr.sewatech.demo.jdbc.common.PersonDAO;

public class PersonMyBatisDAO extends GenericMyBatisDAO<Person> implements PersonDAO {

    public PersonMyBatisDAO(DataSource dataSource) {
        super(PersonMapper.class, dataSource);
    }

    @Override
    public List<Person> findByNameLike(String name) {
        try (SqlSession session = openSession()) {
            return session.getMapper(PersonMapper.class).findByNameLike(name);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

}
