package fr.sewatech.demo.jdbc.common;

import java.sql.SQLException;

public class DAOException extends RuntimeException {
    public DAOException(SQLException e) {
        super(e);
    }

    public DAOException(String message) {
        super(message);
    }
}
