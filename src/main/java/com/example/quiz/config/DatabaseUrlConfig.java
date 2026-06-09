package com.example.quiz.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
@Profile("!prod")
public class DatabaseUrlConfig {

    @Bean
    public DataSource dataSource() {
        String jdbcUrl = System.getenv("JDBC_DATABASE_URL");
        String dbUser = System.getenv("DB_USERNAME");
        String dbPass = System.getenv("DB_PASSWORD");

        if (jdbcUrl != null && !jdbcUrl.isBlank()) {
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl(jdbcUrl);
            if (dbUser != null) ds.setUsername(dbUser);
            if (dbPass != null) ds.setPassword(dbPass);
            ds.setDriverClassName("org.postgresql.Driver");
            return ds;
        }

        String databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl != null && !databaseUrl.isBlank()) {
            try {
                // DATABASE_URL typically: postgres://user:pass@host:port/db
                URI uri = new URI(databaseUrl);
                String userInfo = uri.getUserInfo();
                String username = null;
                String password = null;
                if (userInfo != null) {
                    String[] parts = userInfo.split(":", 2);
                    username = parts[0];
                    if (parts.length > 1) password = parts[1];
                }
                String host = uri.getHost();
                int port = uri.getPort();
                String path = uri.getPath();
                String dbName = path != null && path.startsWith("/") ? path.substring(1) : path;
                String constructedJdbc = String.format("jdbc:postgresql://%s:%d/%s", host, port, dbName);

                HikariDataSource ds = new HikariDataSource();
                ds.setJdbcUrl(constructedJdbc);
                if (username != null) ds.setUsername(username);
                if (password != null) ds.setPassword(password);
                ds.setDriverClassName("org.postgresql.Driver");
                return ds;
            } catch (Exception ex) {
                throw new IllegalStateException("Invalid DATABASE_URL format", ex);
            }
        }

        return null; // fallback to Spring Boot auto-configuration (e.g., for tests)
    }
}
