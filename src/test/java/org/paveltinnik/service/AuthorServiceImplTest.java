//package org.paveltinnik.service;
//
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.paveltinnik.dto.AuthorDTO;
//import org.paveltinnik.mapper.Mapper;
//import org.paveltinnik.model.Author;
//import org.paveltinnik.repository.AuthorRepository;
//import org.paveltinnik.repository.impl.AuthorRepositoryImpl;
//import org.paveltinnik.service.impl.AuthorServiceImpl;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@Testcontainers
//class AuthorServiceImplTest {
//
//    @Container
//    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
//            .withDatabaseName("work2")
//            .withUsername("postgres")
//            .withPassword("postgres");
//
//    @Mock
//    private AuthorRepository authorRepository;
//
//    @InjectMocks
//    private AuthorServiceImpl authorService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this); // Инициализация моков
//    }
//
//    @Test
//    void save_ShouldInsertAuthor() throws SQLException {
//        // Arrange
//        AuthorDTO authorDTO = new AuthorDTO(null, "Test Author");
//        Author author = Mapper.toAuthor(authorDTO);
//
//        // Mocking repository method
//        doNothing().when(authorRepository).save(any(Author.class));
//
//        // Act
//        authorService.save(authorDTO);
//
//        // Assert
//        verify(authorRepository, times(1)).save(any(Author.class));
//    }
//
//    @Test
//    void findById_ShouldReturnAuthor() throws SQLException {
//        // Arrange
//        Author author = new Author(1L, "Test Author");
//        when(authorRepository.findById(1L)).thenReturn(author);
//
//        // Act
//        AuthorDTO foundAuthor = authorService.findById(1L);
//
//        // Assert
//        assertNotNull(foundAuthor);
//        assertEquals("Test Author", foundAuthor.getName());
//        verify(authorRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void findAll_ShouldReturnAllAuthors() throws SQLException {
//        // Arrange
//        Author author1 = new Author(1L, "Author 1");
//        Author author2 = new Author(2L, "Author 2");
//        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));
//
//        // Act
//        List<AuthorDTO> authors = authorService.findAll();
//
//        // Assert
//        assertEquals(2, authors.size());
//        assertEquals("Author 1", authors.get(0).getName());
//        assertEquals("Author 2", authors.get(1).getName());
//        verify(authorRepository, times(1)).findAll();
//    }
//
//    @Test
//    void update_ShouldModifyAuthor() throws SQLException {
//        // Arrange
//        AuthorDTO authorDTO = new AuthorDTO(1L, "Updated Author");
//        Author author = Mapper.toAuthor(authorDTO);
//
//        // Mocking update method
//        doNothing().when(authorRepository).update(any(Author.class));
//
//        // Act
//        authorService.update(authorDTO);
//
//        // Assert
//        verify(authorRepository, times(1)).update(any(Author.class));
//    }
//
//    @Test
//    void delete_ShouldRemoveAuthor() throws SQLException {
//        // Arrange
//        Long authorId = 1L;
//
//        // Act
//        authorService.delete(authorId);
//
//        // Assert
//        verify(authorRepository, times(1)).delete(authorId);
//    }
//}
