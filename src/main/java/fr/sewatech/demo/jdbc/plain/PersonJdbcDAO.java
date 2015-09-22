package fr.sewatech.demo.jdbc.plain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import fr.sewatech.demo.jdbc.common.DAOException;
import fr.sewatech.demo.jdbc.common.Person;
import fr.sewatech.demo.jdbc.common.PersonDAO;

public class PersonJdbcDAO implements PersonDAO {
    private static final String FIND_ALL_QUERY = "SELECT key, name, forname FROM person ";
    private static final String FIND_BY_KEY_QUERY = FIND_ALL_QUERY + "WHERE key=? ";
    private static final String FIND_BY_NAME_LIKE_QUERY = FIND_ALL_QUERY + "WHERE upper(name) like upper(?)";
    private static final String UPDATE_QUERY = "UPDATE person SET name=?, forname=? WHERE key=?";
    private static final String CREATE_QUERY = "INSERT INTO person (name, forname) VALUES (?, ?)";
    private static final String REMOVE_QUERY = "DELETE FROM person WHERE key=?";

    private final DataSource dataSource;

    public PersonJdbcDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Person findByKey(Long key) {
        if (key == null) {
            throw new NullPointerException("key");
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_KEY_QUERY);
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
    public List<Person> findByAll() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery();

            return toEntityList(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Person> findByNameLike(String name) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_LIKE_QUERY);
            statement.setString(1, "%"+name+"%");
            ResultSet resultSet = statement.executeQuery();

            return toEntityList(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Person update(Person person) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, person.getName());
            statement.setString(2, person.getForname());
            statement.setLong(3, person.getKey());
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
    public Person create(Person person) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getName());
            statement.setString(2, person.getForname());
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
    public void remove(Person person) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(REMOVE_QUERY);
            statement.setLong(1, person.getKey());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


    private List<Person> toEntityList(ResultSet resultSet) throws SQLException {
        List<Person> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(toEntity(resultSet));
        }
        return result;
    }

    private Person toEntity(ResultSet resultSet) throws SQLException {
        return new Person(resultSet.getLong("key"), resultSet.getString("name"), resultSet.getString("forname"));
    }
}
