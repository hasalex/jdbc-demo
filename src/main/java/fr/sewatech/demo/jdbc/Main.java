package fr.sewatech.demo.jdbc;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.sewatech.demo.jdbc.common.Person;
import fr.sewatech.demo.jdbc.common.PersonDAO;
import fr.sewatech.demo.jdbc.internal.Database;
import fr.sewatech.demo.jdbc.mybatis.PersonMyBatisDAO;
import fr.sewatech.demo.jdbc.plain.PersonJdbcDAO;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Let's start...");
        DataSource dataSource = Database.create();

        logger.info("=========== Plain JDBC example ===========");
        demo(new PersonJdbcDAO(dataSource));

        logger.info("=========== MyBatis example ===========");
        demo(new PersonMyBatisDAO(dataSource));
    }

    private static void demo(PersonDAO personDAO) {
        Person person = personDAO.findByKey(1L);
        logger.info("Found " + person);

        Person newPerson = new Person("Roe", "Richard");
        logger.info("Created " + personDAO.create(newPerson));
        logger.info("Found after creation " + personDAO.findByKey(newPerson.getKey()));

        newPerson.setForname("Richie");
        newPerson.setName("Roo");
        logger.info("Updated " + personDAO.update(newPerson));
        logger.info("Found after update " + personDAO.findByKey(newPerson.getKey()));

        personDAO.remove(newPerson);
        logger.info("All found after remove " + personDAO.findByAll());
    }

}
