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

import fr.sewatech.demo.jdbc.common.DAO;
import fr.sewatech.demo.jdbc.common.DAOException;

public class GenericMyBatisDAO<E> implements DAO<E> {

    private final SqlSessionFactory sessionFactory;
    private DataSource dataSource;
    private Class<? extends GenericMapper<E>> mapperClass;

    public GenericMyBatisDAO(Class<? extends GenericMapper<E>> mapperClass, DataSource dataSource) {
        this.mapperClass = mapperClass;
        this.dataSource = dataSource;

        LogFactory.useSlf4jLogging();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("demo", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(PersonMapper.class);
        sessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    @Override
    public E findByKey(Long key) {
        if (key == null) {
            throw new NullPointerException("key");
        }

        try (SqlSession session = sessionFactory.openSession(dataSource.getConnection())) {
            return session.getMapper(mapperClass).findByKey(key);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<E> findByAll() {
        try (SqlSession session = sessionFactory.openSession(dataSource.getConnection())) {
            return session.getMapper(mapperClass).findAll();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public E update(E person) {
        try (SqlSession session = sessionFactory.openSession(dataSource.getConnection())) {
            session.getMapper(mapperClass).update(person);
            return person;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public E create(E person) {
        try (SqlSession session = sessionFactory.openSession(dataSource.getConnection())) {
            session.getMapper(mapperClass).create(person);
            return person;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void remove(E person) {
        try (SqlSession session = sessionFactory.openSession(dataSource.getConnection())) {
            session.getMapper(mapperClass).remove(person);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    protected SqlSession openSession() throws SQLException {
        return sessionFactory.openSession(dataSource.getConnection());
    }

}
