package fr.sewatech.demo.jdbc.internal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database {
    public static final String DB_URL = "jdbc:h2:mem:demo;DB_CLOSE_DELAY=-1";

    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    private final JdbcDataSource dataSource;

    public static DataSource create() {
        Database database = new Database();
        database.populate();
        database.startWebConsole();

        return database.dataSource;
    }

    public Database() {
        try {
            logger.debug("Starting H2");
            dataSource = new JdbcDataSource();
            dataSource.setUrl(DB_URL);
            dataSource.getConnection();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    private void populate() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(DB_URL, null, null);
        flyway.migrate();
    }

    private void startWebConsole() {
        try {
            logger.debug("Starting H2 console server");
            Server.createWebServer("-webDaemon").start();
        } catch (SQLException e) {
            logger.warn("Fail to start H2 Web console", e);
        }
    }

}
