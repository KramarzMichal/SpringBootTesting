package Spring.Boot.Testing.Spring_Boot_Testing.service.impl;

import Spring.Boot.Testing.Spring_Boot_Testing.entities.Book;
import Spring.Boot.Testing.Spring_Boot_Testing.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class BookServiceImplTest {
    private BookRepository bookRepository = mock(BookRepository.class);
    private BookServiceImpl bookService = new BookServiceImpl(bookRepository);
    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(
                1L,
                "Władca Pierścieni",
                "Tolkien", "123456AB",
                1950, "fantasy",
                new BigDecimal("49.99"),
                true,
                Arrays.asList(5, 6, 7, 8));
    }

    @DisplayName("Zapisywanie książki")
    @Test
    void testSavedbook() {
        //given
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        //when
        Book savedBook = bookService.saveBook(book);
        //then
        assertThat(savedBook).isNotNull();
        assertThat(savedBook).isEqualTo(book);
    }

    @DisplayName("Pobieranie wszystkich książek")
    @Test
    void shouldReturnAllBooks() {
        //given
        List<Book> givenBooks = List.of(book, new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(givenBooks);
        //when
        List<Book> receivedBooks = bookService.getAllBooks();
        //then
        assertThat(receivedBooks).isNotNull();
        assertThat(receivedBooks).isNotEmpty();
        assertThat(receivedBooks.size()).isEqualTo(3);

    }
    @DisplayName("Pobieranie książki po id")
    @Test
    void shouldReturnBookByItsId (){
        //given
        given(bookRepository.findBookById(anyLong())).willReturn(Optional.of(book));
        //when
        Optional<Book> returnedBook = bookService.getBookById(book.getId());
        //then
        assertThat(returnedBook).isNotNull();
        assertThat(returnedBook).isEqualTo(Optional.of(book));

    }
    @DisplayName("Uaktualnianie książki")
    @Test
    void shouldReturnUpdatedBook(){
        //given
        book.setAuthor("Kowalski");
        when(bookRepository.save(book)).thenReturn(book);
        //when
        Book updatedBook = bookService.updateBook(book);
        //then
        assertThat(updatedBook.getAuthor()).isEqualTo(book.getAuthor());
        assertThat(updatedBook.getAuthor()).isEqualTo("Kowalski");
    }
}