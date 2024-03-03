package Spring.Boot.Testing.Spring_Boot_Testing.repository;

import Spring.Boot.Testing.Spring_Boot_Testing.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(
                1L,
                "Władca Pierścieni",
                "J.R.R Tolkien",
                "1234567AB",
                1950,
                "Fantasy",
                new BigDecimal("49.99"),
                true,
                Arrays.asList(5, 4, 9, 3)
        );
    }

    @Test
    @DisplayName("Zapisywanie książki")
    void givenBook_whenSaved_thenReturnedSavedBook() {
        //given
        //when
        Book savedBook = bookRepository.save(book);
        //then
        assertThat(savedBook).isNotNull();
        assertThat(savedBook).isEqualTo(book);
    }

    @Test
    @DisplayName("Pobieranie wszystkich książek")
    void givenBooks_whenfindAll_thenReturnedBookList() {
        //given
        Book book1 = new Book(2L, "Krzyżacy", "Sinkiewicz", "987654321", 1900,
                "novel", new BigDecimal(29.99), true, Arrays.asList(5, 4, 6));
        bookRepository.save(book);
        bookRepository.save(book1);
        //when
        List<Book> bookList = bookRepository.findAll();
        //then
        assertThat(bookList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Wyszukiwanie książki po Id")
    void givenBook_whenFindbyId_thenReturnSavedBook() {
        //given
        bookRepository.save(book);
        //when
       Book savedBook = bookRepository.findById(book.getId()).get();
        //then
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isEqualTo(book.getId());
    }
    @Test
    @DisplayName("Uaktualnienie danych książki")
    void givenBook_thenUpdateBookData_thenReturnUpdatedBook (){
        //given
        bookRepository.save(book);
        book.setAuthor("Mickiewicz");
        book.setPrice(new BigDecimal("99.99"));

        //when
       Book updatedBook =  bookRepository.save(book);
        //then
        assertThat(book.getAuthor()).isEqualTo("Mickiewicz");
        assertThat(book.getPrice()).isEqualTo(new BigDecimal("99.99"));
    }
    @Test
    @DisplayName("Liczenie książek po roku publikacji")
    void testCountBooksByYearPubished(){
        //given
        bookRepository.save(book);
        //when
        int count = bookRepository.countBooksByPublishedYear(book.getPublishedYear());
        //then
        assertThat(count).isEqualTo(1);

    }
    @Test
    @DisplayName("Obliczenie średniej oceny dla autora")
    void testCalculatingAverageRatingByAuthor(){
        //given
       Book book2 = new Book(2L, "Pan Tadeusz", "Tolkien", "12354899", 1870, "novel", new BigDecimal("29.99"), true, Arrays.asList(5, 7, 8));
       Book book3 = new Book(2L, "Pan Tadeusz", "Tolkien", "4561489665876", 1870, "novel", new BigDecimal("29.99"), true, Arrays.asList(2, 4, 3));
       Book book4 = new Book(2L, "Pan Tadeusz", "Tolkien", "456168775876", 1870, "novel", new BigDecimal("29.99"), true, Arrays.asList(3,2,1,5));
        bookRepository.save(book);
        bookRepository.save(book4);
        bookRepository.save(book2);
        bookRepository.save(book3);
        //when
        List<Book> bookList = bookRepository.findByAuthor(book.getAuthor());
        double avgRating = bookRepository.calculateAverageRatingByAuthor(book.getAuthor());
        //then
        assertThat(avgRating).isEqualTo(bookList.stream().
              mapToDouble(Book::countAvgRating)
              .average()
              .orElse(0.0d));
    }
    @DisplayName("Test wyszukiwania książki po ISBN - pozytywny")
    @Test
    void testFindBookByISBN() {
        // given
        bookRepository.save(book);
        String isbn = "1234567AB";

        // when
        Book foundBook = bookRepository.findBookByIsbn(isbn).get();

        // then
        assertThat(foundBook).isNotNull();
        assertThat(foundBook.getIsbn()).isEqualTo(isbn);
    }

    @DisplayName("Test wyszukiwania książki po ISBN - negatywny")
    @Test
    void testFindBookByISBNNegativePath() {
        // given
        bookRepository.save(book);
        String isbn = "1234567890123";

        // when
        Optional<Book> foundBook = bookRepository.findBookByIsbn(isbn);

        // then
        assertThat(foundBook).isEmpty();

    }
    @DisplayName("wyszukiwania książek tańszych niż podana cena")
    @Test
    void testFindBooksCheaperThan() {
        // given
        bookRepository.save(book);
        BigDecimal price = new BigDecimal("50.00");

        // when
        List<Book> books = bookRepository.findBooksCheaperThan(price);

        // then
        assertThat(books).isNotEmpty();
    }

    @DisplayName("wyszukiwania książek tańszych niż podana cenav2")
    @Test
    void testFindBooksCheaperThanV2() {
        // given
        bookRepository.save(book);
        BigDecimal price = new BigDecimal("50.00");

        // when
        List<Book> books = bookRepository.findByPriceLessThan(price);

        // then
        assertThat(books).isNotEmpty();
    }

    @DisplayName("wyszukiwania książek tańszych niż podana cenav3")
    @Test
    void testFindBooksCheaperThanV3() {
        // given
        bookRepository.save(book);
        BigDecimal price = new BigDecimal("50.00");

        // when
        List<Book> books = bookRepository.findAllByPriceBefore(price);

        // then
        assertThat(books).isNotEmpty();
    }

    @DisplayName("wyszukiwania wszystkich dostępnych książek (pozytywny)")
    @Test
    void testFindAllAvailableBooks() {
        // given
        Book newBook1 = new Book(2L, "Władca Pierścieni", "J.R.R Tolkien", "1234567AA", 1950, "Fantasy", new BigDecimal("49.99"), true, Arrays.asList(10, 4, 9, 3));
        Book newBook2 = new Book(3L, "Władca Pierścieni", "J.R.R Tolkien", "1234567AC", 1950, "Fantasy", new BigDecimal("49.99"), false, Arrays.asList(9, 4, 9, 12));
        Book newBook3 = new Book(4L, "Władca Pierścieni", "J.R.R Tolkien", "1234567AD", 1950, "Fantasy", new BigDecimal("49.99"), false, Arrays.asList(9, 6, 9, 3));

        bookRepository.save(book);
        bookRepository.save(newBook1);
        bookRepository.save(newBook2);
        bookRepository.save(newBook3);

        // when
        List<Book> books = bookRepository.findAllByAvailableIsTrue();

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(Book::getAvailable);
    }

    @DisplayName("wyszukiwania wszystkich dostępnych książek (negatywny)")
    @Test
    void testFindAllAvailableBooksNegativePath() {
        // given
        Book newBook2 = new Book(3L, "Władca Pierścieni", "J.R.R Tolkien", "1234567AC", 1950, "Fantasy", new BigDecimal("49.99"), false, Arrays.asList(9, 4, 9, 12));
        Book newBook3 = new Book(4L, "Władca Pierścieni", "J.R.R Tolkien", "1234567AD", 1950, "Fantasy", new BigDecimal("49.99"), false, Arrays.asList(9, 6, 9, 3));

        bookRepository.save(newBook2);
        bookRepository.save(newBook3);

        // when
        List<Book> books = bookRepository.findAllByAvailableIsTrue();

        // then
        assertThat(books).isEmpty();
    }

    @DisplayName("wyszukiwania książek po autorze")
    @Test
    void testFindBooksByAuthor() {
        // given
        Book newBook2 = new Book(3L, "Władca Pierścieni", "J.R.R Tolkien", "1234567AC", 1950, "Fantasy", new BigDecimal("49.99"), false, Arrays.asList(9, 4, 9, 12));
        Book newBook3 = new Book(4L, "Władca Pierścieni", "Jarek", "1234567AD", 1950, "Fantasy", new BigDecimal("49.99"), false, Arrays.asList(9, 6, 9, 3));

        bookRepository.save(book);
        bookRepository.save(newBook2);
        bookRepository.save(newBook3);
        String author = book.getAuthor();
        // when
        List<Book> books = bookRepository.findByAuthor(author);

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(b -> b.getAuthor().equals(author));
    }

    @DisplayName("Test wyszukiwania niedawno wydanych książek w danym gatunku")
    @Test
    void testFindRecentBooksByGenre() {
        // given
        Book newBook2 = new Book(3L, "Władca Pierścieni", "J.R.R Tolkien", "1234567AC", 2000, "Fantasy", new BigDecimal("49.99"), false, Arrays.asList(9, 4, 9, 12));
        Book newBook3 = new Book(4L, "Władca Pierścieni", "Jarek", "1234567AD", 1900, "Fantasy", new BigDecimal("49.99"), false, Arrays.asList(9, 6, 9, 3));

        bookRepository.save(book);
        bookRepository.save(newBook2);
        bookRepository.save(newBook3);
        String genre = book.getGenre();
        int year = 1900;
        // when
        List<Book> books = bookRepository.findRecentBooksByGenre(year, genre);

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(b -> b.getPublishedYear() > year && b.getGenre().equals(genre));
    }

    @DisplayName("Test wyszukiwania niedawno wydanych książek w danym gatunku")
    @Test
    void testFindRecentBooksByGenreV2() {
        // given
        Book newBook2 = new Book(3L, "Władca Pierścieni", "J.R.R Tolkien", "1234567AC", 1900, "Fantasy", new BigDecimal("49.99"), false, Arrays.asList(9, 4, 9, 12));
        Book newBook3 = new Book(4L, "Władca Pierścieni", "Jarek", "1234567AD", 1900, "Fantasy", new BigDecimal("49.99"), false, Arrays.asList(9, 6, 9, 3));

        bookRepository.save(book);
        bookRepository.save(newBook2);
        bookRepository.save(newBook3);
        String genre = book.getGenre();
        int year = 1900;
        // when
        List<Book> books = bookRepository.findAllByPublishedYearAndGenre(year, genre);

        // then
        books.forEach(System.out::println);
        assertThat(books).isNotEmpty();
        assertThat(books.size()).isEqualTo(2);
//        assertThat(books).allMatch(b -> b.getPublishedYear() > year && b.getGenre().equals(genre));
    }

    @DisplayName("liczenia wszystkich książek")
    @Test
    void testCountAllBooks() {
        // given
        Book newBook2 = new Book(3L, "Władca Pierścieni", "J.R.R Tolkien", "1234567AC", 1900, "Fantasy", new BigDecimal("49.99"), false, Arrays.asList(9, 4, 9, 12));
        Book newBook3 = new Book(4L, "Władca Pierścieni", "Jarek", "1234567AD", 1900, "Fantasy", new BigDecimal("49.99"), false, Arrays.asList(9, 6, 9, 3));

        bookRepository.save(book);
        bookRepository.save(newBook2);
        bookRepository.save(newBook3);

        // when
        long count = bookRepository.count();

        // then
        assertThat(count).isEqualTo(3);
    }

    //    -
    @DisplayName("liczenia wszystkich książek z danego gatunku")
    @Test
    void testCountAllBooksByGenre() {
        // given
        Book newBook2 = new Book(3L, "Władca Pierścieni", "J.R.R Tolkien", "1234567AC", 1900, "Fantasy", new BigDecimal("49.99"), false, Arrays.asList(9, 4, 9, 12));
        Book newBook3 = new Book(4L, "Władca Pierścieni", "Jarek", "1234567AD", 1900, "Fantasy", new BigDecimal("49.99"), false, Arrays.asList(9, 6, 9, 3));

        bookRepository.save(book);
        bookRepository.save(newBook2);
        bookRepository.save(newBook3);

        // when
        long count = bookRepository.countByGenre(book.getGenre());

        // then
        assertThat(count).isEqualTo(3);
    }

    //    - znajdowania najdroższej książki
    @DisplayName("znajdowania najdroższej książki")
    @Test
    void testFindMostExpensiveBook() {
        // given
        Book newBook2 = new Book(3L, "Władca Pierścieni", "J.R.R Tolkien", "1234567AC", 1900, "Fantasy", new BigDecimal("149.99"), false, Arrays.asList(9, 4, 9, 12));
        Book newBook3 = new Book(4L, "Władca Pierścieni", "Jarek", "1234567AD", 1900, "Fantasy", new BigDecimal("349.99"), false, Arrays.asList(9, 6, 9, 3));

        bookRepository.save(book);
        bookRepository.save(newBook2);
        bookRepository.save(newBook3);

        // when
        List<Book> books = bookRepository.findMostExpensiveBooks();

        // then
        assertThat(books.size()).isEqualTo(1);
    }


    @DisplayName("znajdowania najtańszej książki")
    @Test
    void testFindLesExpensiveBook() {
        // given
        Book newBook2 = new Book(3L, "Władca Pierścieni", "J.R.R Tolkien", "1234567AC", 1900, "Fantasy", new BigDecimal("149.99"), false, Arrays.asList(9, 4, 9, 12));
        Book newBook3 = new Book(4L, "Władca Pierścieni", "Jarek", "1234567AD", 1900, "Fantasy", new BigDecimal("349.99"), false, Arrays.asList(9, 6, 9, 3));

        bookRepository.save(book);
        bookRepository.save(newBook2);
        bookRepository.save(newBook3);

        // when
        List<Book> books = bookRepository.findLesExpensiveBooks();

        // then
        assertThat(books.size()).isEqualTo(1);
    }

    @DisplayName("znajdowania książek z odpowiednimi ocenami powyżej podanej wartości")
    @Test
    void testFindBooksWithRatingsAboveThreshold() {
        // given
        Book newBook2 = new Book(3L, "Władca Pierścieni", "J.R.R Tolkien", "1234567AC", 1900, "Fantasy", new BigDecimal("149.99"), false, Arrays.asList(1, 1, 1, 1));
        Book newBook3 = new Book(4L, "Władca Pierścieni", "Jarek", "1234567AD", 1900, "Fantasy", new BigDecimal("349.99"), false, Arrays.asList(2, 2, 2, 2));

        bookRepository.save(book);
        bookRepository.save(newBook2);
        bookRepository.save(newBook3);
        int ratingThreshold = 4;
        // when
        List<Book> books = bookRepository.findBooksWithRatingsAbove(ratingThreshold);
        books.forEach(book -> System.out.printf("Książka z ratingiem %s: oraz id: %d%n", book.getRatings(), book.getId()));
        // then

        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(b -> b.getRatings().stream().anyMatch(rating -> rating > ratingThreshold));


    }


    @DisplayName("obliczania średniej oceny dla autora")
    @Test
    void testCalculateAverageRatingByAuthor() {
        // given
        Book newBook1 = new Book(2L, "Władca Pierścieni", "J.R.R Tolkien", "1234567AA", 1950, "Fantasy", new BigDecimal("49.99"), true, Arrays.asList(10, 4, 9, 3));
        Book newBook2 = new Book(3L, "Władca Pierścieni", "J.R.R Tolkien", "1234567AC", 1950, "Fantasy", new BigDecimal("49.99"), true, Arrays.asList(9, 4, 9, 12));
        Book newBook3 = new Book(4L, "Władca Pierścieni", "J.R.R Tolkien", "1234567AD", 1950, "Fantasy", new BigDecimal("49.99"), true, Arrays.asList(9, 6, 9, 3));

        bookRepository.save(book);
        bookRepository.save(newBook1);
        bookRepository.save(newBook2);
        bookRepository.save(newBook3);

        List<Book> bookList = bookRepository.findByAuthor(book.getAuthor());
        Double avgRating = bookRepository.calculateAverageRatingByAuthor(book.getAuthor());

        // this
        assertThat(avgRating).isNotNull();
        assertThat(avgRating).isEqualTo(bookList
                .stream()
                .mapToDouble(Book::countAvgRating)
                .average()
                .orElse(0.0));
    }
}