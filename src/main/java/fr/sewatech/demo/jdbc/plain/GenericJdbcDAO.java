package fr.sewatech.demo.jdbc.plain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import fr.sewatech.demo.jdbc.common.DAO;
import fr.sewatech.demo.jdbc.common.DAOException;
import fr.sewatech.demo.jdbc.common.GenericEntity;

public abstract class GenericJdbcDAO<E extends GenericEntity> implements DAO<E> {

    private final DataSource dataSource;

    public GenericJdbcDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public E findByKey(Long key) {
        if (key == null) {
            throw new NullPointerException("key");
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(findByKeyQuery());
            statement.setLong(1, key);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return toEntity(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<E> findByAll() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(findAllQuery());
            ResultSet resultSet = statement.executeQuery();

            return toEntityList(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public E update(E person) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(updateQuery());
            addParameters(statement, person);
            int rows = statement.executeUpdate();

            if (rows > 0) {
                return person;
            } else {
                throw new DAOException("No row updated");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public E create(E person) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(createQuery(), Statement.RETURN_GENERATED_KEYS);
            addParameters(statement, person);
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                person.setKey(keys.getLong(1));
            }

            return person;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void remove(E person) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(removeQuery());
            statement.setLong(1, person.getKey());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    protected List<E> toEntityList(ResultSet resultSet) throws SQLException {
        List<E> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(toEntity(resultSet));
        }
        return result;
    }

    protected abstract E toEntity(ResultSet resultSet) throws SQLException;

    protected abstract void addParameters(PreparedStatement resultSet, E entity) throws SQLException;

    protected abstract String findByKeyQuery();

    protected abstract String findAllQuery();

    protected abstract String updateQuery();

    protected abstract String createQuery();

    protected abstract String removeQuery();

    protected Connection openConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
