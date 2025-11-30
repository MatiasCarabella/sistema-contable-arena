package util;

import config.DatabaseConfig;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @deprecated Use {@link DatabaseConfig#getConnection()} instead
 */
@Deprecated(since = "2.0", forRemoval = true)
public class DBConnection {
    private DBConnection() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Connection getConnection() throws SQLException {
        return DatabaseConfig.getConnection();
    }
}
