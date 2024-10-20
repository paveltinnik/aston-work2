//package org.paveltinnik.repository;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.paveltinnik.config.DatabaseConfig;
//import org.paveltinnik.model.Author;
//import org.paveltinnik.repository.impl.AuthorRepositoryImpl;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@Testcontainers
//public class AuthorRepositoryImplTest {
//
//    @Container
//    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
//            .withDatabaseName("test")
//            .withUsername("postgres")
//            .withPassword("postgres");
//
//    private AuthorRepository authorRepository;
//
//    @BeforeEach
//    public void setUp() throws SQLException {
//        // Настройка соединения с базой данных из контейнера
//        String jdbcUrl = postgresContainer.getJdbcUrl();
//        String username = postgresContainer.getUsername();
//        String password = postgresContainer.getPassword();
//
//        // Инициализация DatabaseConfig с использованием параметров контейнера
//        DatabaseConfig.setDataSource(jdbcUrl, username, password);
//
//        authorRepository = new AuthorRepositoryImpl();
//
//        // Инициализация базы данных
//        initDatabase();
//    }
//
//    @AfterEach
//    public void drop() throws SQLException {
//        // Настройка соединения с базой данных из контейнера
//        String jdbcUrl = postgresContainer.getJdbcUrl();
//        String username = postgresContainer.getUsername();
//        String password = postgresContainer.getPassword();
//
//        // Инициализация DatabaseConfig с использованием параметров контейнера
//        DatabaseConfig.setDataSource(jdbcUrl, username, password);
//
//        authorRepository = new AuthorRepositoryImpl();
//
//        // Инициализация базы данных
//        dropDatabase();
//    }
//
//    private void dropDatabase() throws SQLException {
//        try (Connection connection = DatabaseConfig.getConnection()) {
//            String initScript = """
//                DROP TABLE IF EXISTS Author
//                """;
//            try (var statement = connection.createStatement()) {
//                statement.execute(initScript);
//            }
//        }
//    }
//
//    private void initDatabase() throws SQLException {
//        try (Connection connection = DatabaseConfig.getConnection()) {
//            String initScript = """
//                CREATE TABLE IF NOT EXISTS Author (
//                    id SERIAL PRIMARY KEY,
//                    name VARCHAR(255) NOT NULL
//                );
//                """;
//            try (var statement = connection.createStatement()) {
//                statement.execute(initScript);
//            }
//        }
//    }
//
//    @Test
//    public void save_ShouldInsertAuthor() throws SQLException {
//        Author author = new Author(null, "Test Author");
//        authorRepository.save(author);
//
//        List<Author> authors = authorRepository.findAll();
//        assertEquals(1, authors.size());
//        assertEquals("Test Author", authors.get(0).getName());
//    }
//
//    @Test
//    public void findById_ShouldReturnAuthor() throws SQLException {
//        Author author = new Author(null, "Test Author");
//        authorRepository.save(author);
//
//        Author foundAuthor = authorRepository.findById(1L);
//        assertNotNull(foundAuthor);
//        assertEquals("Test Author", foundAuthor.getName());
//    }
//
//    @Test
//    public void findAll_ShouldReturnAllAuthors() throws SQLException {
//        authorRepository.save(new Author(null, "Author 1"));
//        authorRepository.save(new Author(null, "Author 2"));
//
//        List<Author> authors = authorRepository.findAll();
//        assertEquals(2, authors.size());
//    }
//}
