package Spring.Boot.Testing.Spring_Boot_Testing.service;

import Spring.Boot.Testing.Spring_Boot_Testing.entities.Book;
import Spring.Boot.Testing.Spring_Boot_Testing.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
public interface BookService {

    List<Book> getAllBooks();

    Book saveBook (Book bookToSave);

    Optional<Book> getBookById (long id);

    Book updateBook (Book bookToUpdate);

    Optional<Book> updateBook (long id, Book bookToUpdate);
    Book getBookByIsbn (String Isbn);

    List<Book> getBooksByGenre (String genre);
    List<Book> getBooksByAuthor (String author);
    List<Book> getBooksByPublishedYear (int publishedYear);
    List<Book> getAllAvailableBooks ();
    List <Book> getBooksCheaperThen (BigDecimal price);
    List<Book> getMostExpensiveBooks ();
    List<Book> getBooksWithRatingAbove (int ratingTreshold);
    long countAllBooks ();
    long countBooksByGenre (String genre);
    Double calculateAverageRatingByAuthor (String author);
    void deleteBook (long id);
    Book updateBookAvailibility (Long id, boolean isAvailable);
    Book addRatingToBook (Long id, int rating);
}
