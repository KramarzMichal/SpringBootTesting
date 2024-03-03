package Spring.Boot.Testing.Spring_Boot_Testing.repository;

import Spring.Boot.Testing.Spring_Boot_Testing.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    int countBooksByPublishedYear(int publishedYear);

    List<Book> findByGenre(String genre);

    List<Book> findByAuthor(String author);

    @Query("SELECT AVG(r) FROM Book as b JOIN b.ratings as r WHERE b.author = :author")
    double calculateAverageRatingByAuthor(String author);

    Optional<Book> findBookByIsbn(String isbn);

    @Query(value = "SELECT * FROM books WHERE price < ?1", nativeQuery = true)
    List<Book> findBooksCheaperThan(BigDecimal price);

    List<Book> findByPriceLessThan(BigDecimal price);

    List<Book> findAllByPriceBefore(BigDecimal price);
    //@Query(value = "SELECT * FROM books WHERE available = true", nativeQuery = true)
    List<Book> findAllByAvailableIsTrue();

    @Query(value = "SELECT * FROM books WHERE published_year >:year AND genre =:genre", nativeQuery = true)
    List<Book> findRecentBooksByGenre(int year, String genre);

    List<Book> findAllByPublishedYearAndGenre(int year, String genre);

    long countByGenre(String genre);

    @Query("SELECT b FROM Book b ORDER BY b.price DESC LIMIT 1")
    List<Book> findMostExpensiveBooks();
    @Query("SELECT b FROM Book b ORDER BY b.price ASC LIMIT 1")
    List<Book> findLesExpensiveBooks();

    @Query("SELECT b FROM Book b WHERE :ratingThreshold < SOME (SELECT r FROM b.ratings r)")
    List<Book> findBooksWithRatingsAbove(int ratingThreshold);
}
