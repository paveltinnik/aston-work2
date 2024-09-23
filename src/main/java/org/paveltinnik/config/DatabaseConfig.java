package org.paveltinnik.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {

    private static HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();
            Properties properties = new Properties();

            // Load database configuration from a properties file
            properties.load(DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties"));
            config.setJdbcUrl(properties.getProperty("jdbc.url"));
            config.setUsername(properties.getProperty("jdbc.username"));
            config.setPassword(properties.getProperty("jdbc.password"));
            config.setDriverClassName(properties.getProperty("jdbc.driver"));

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize the data source", e);
        }
    }

    /**
     * Configures the HikariCP data source.
     *
     * @param jdbcUrl  JDBC URL of the database
     * @param username Database username
     * @param password Database password
     */
    public static void setDataSource(String jdbcUrl, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10); // Set the max pool size
        config.setConnectionTimeout(30000); // Connection timeout in milliseconds

        dataSource = new HikariDataSource(config);
    }

    /**
     * Gets a connection from the data source.
     *
     * @return A Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new IllegalStateException("Data source has not been initialized.");
        }
        return dataSource.getConnection();
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Closes the data source.
     */
    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}