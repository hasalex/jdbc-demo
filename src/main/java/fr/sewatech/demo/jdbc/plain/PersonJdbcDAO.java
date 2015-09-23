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

public class PersonJdbcDAO extends GenericJdbcDAO<Person> implements PersonDAO {
    private static final String FIND_ALL_QUERY = "SELECT key, name, forname FROM person ";
    private static final String FIND_BY_KEY_QUERY = FIND_ALL_QUERY + "WHERE key=? ";
    private static final String FIND_BY_NAME_LIKE_QUERY = FIND_ALL_QUERY + "WHERE upper(name) like upper(?)";
    private static final String UPDATE_QUERY = "UPDATE person SET name=?, forname=? WHERE key=?";
    private static final String CREATE_QUERY = "INSERT INTO person (name, forname) VALUES (?, ?)";
    private static final String REMOVE_QUERY = "DELETE FROM person WHERE key=?";

    public PersonJdbcDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Person toEntity(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    protected void addParameters(PreparedStatement resultSet, Person entity) throws SQLException {
        resultSet.setString(1, entity.getName());
        resultSet.setString(2, entity.getForname());
        if (entity.getKey() != null) {
            resultSet.setLong(3, entity.getKey());
        }
    }

    @Override
    protected String findByKeyQuery() {
        return FIND_BY_KEY_QUERY;
    }

    @Override
    protected String findAllQuery() {
        return FIND_ALL_QUERY;
    }

    @Override
    protected String updateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String createQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String removeQuery() {
        return REMOVE_QUERY;
    }

    @Override
    public List<Person> findByNameLike(String name) {
        try (Connection connection = openConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_LIKE_QUERY);
            statement.setString(1, "%"+name+"%");
            ResultSet resultSet = statement.executeQuery();

            return toEntityList(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

}
