package Spring.Boot.Testing.Spring_Boot_Testing.service.impl;

import Spring.Boot.Testing.Spring_Boot_Testing.entities.Book;
import Spring.Boot.Testing.Spring_Boot_Testing.repository.BookRepository;
import Spring.Boot.Testing.Spring_Boot_Testing.service.BookService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book saveBook(Book bookToSave) {
        return bookRepository.save(bookToSave);
    }

    @Override
    public Optional<Book> getBookById(long id) { return bookRepository.findBookById(id);}

    @Override
    public Book updateBook(Book bookToUpdate) {
        return bookRepository.save(bookToUpdate);
    }

    @Override
    public Optional<Book> updateBook(long id, Book bookToUpdate) {
        return Optional.empty();
    }

    @Override
    public Book getBookByIsbn(String Isbn) {
        return null;
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        return null;
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return null;
    }

    @Override
    public List<Book> getBooksByPublishedYear(int publishedYear) {
        return null;
    }

    @Override
    public List<Book> getAllAvailableBooks() {
        return null;
    }

    @Override
    public List<Book> getBooksCheaperThen(BigDecimal price) {
        return null;
    }

    @Override
    public List<Book> getMostExpensiveBooks() {
        return null;
    }

    @Override
    public List<Book> getBooksWithRatingAbove(int ratingTreshold) {
        return null;
    }

    @Override
    public long countAllBooks() {
        return 0;
    }

    @Override
    public long countBooksByGenre(String genre) {
        return 0;
    }

    @Override
    public Double calculateAverageRatingByAuthor(String author) {
        return null;
    }

    @Override
    public void deleteBook(long id) {

    }

    @Override
    public Book updateBookAvailibility(Long id, boolean isAvailable) {
        return null;
    }

    @Override
    public Book addRatingToBook(Long id, int rating) {
        return null;
    }
}
