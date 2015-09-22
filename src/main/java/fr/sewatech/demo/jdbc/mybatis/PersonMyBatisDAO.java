package fr.sewatech.demo.jdbc.mybatis;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import fr.sewatech.demo.jdbc.common.DAOException;
import fr.sewatech.demo.jdbc.common.Person;
import fr.sewatech.demo.jdbc.common.PersonDAO;

public class PersonMyBatisDAO implements PersonDAO {

    private final SqlSessionFactory sessionFactory;
    private DataSource dataSource;

    public PersonMyBatisDAO(DataSource dataSource) {
        this.dataSource = dataSource;

        LogFactory.useSlf4jLogging();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("demo", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(PersonMapper.class);
        sessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    @Override
    public List<Person> findByNameLike(String name) {
        try (SqlSession session = sessionFactory.openSession(dataSource.getConnection())) {
            return session.getMapper(PersonMapper.class).findByNameLike(name);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Person findByKey(Long key) {
        if (key == null) {
            throw new NullPointerException("key");
        }

        try (SqlSession session = sessionFactory.openSession(dataSource.getConnection())) {
            return session.getMapper(PersonMapper.class).findByKey(key);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Person> findByAll() {
        try (SqlSession session = sessionFactory.openSession(dataSource.getConnection())) {
            return session.getMapper(PersonMapper.class).findAll();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Person update(Person person) {
        try (SqlSession session = sessionFactory.openSession(dataSource.getConnection())) {
            session.getMapper(PersonMapper.class).update(person);
            return person;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Person create(Person person) {
        try (SqlSession session = sessionFactory.openSession(dataSource.getConnection())) {
            session.getMapper(PersonMapper.class).create(person);
            return person;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void remove(Person person) {
        try (SqlSession session = sessionFactory.openSession(dataSource.getConnection())) {
            session.getMapper(PersonMapper.class).remove(person);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
