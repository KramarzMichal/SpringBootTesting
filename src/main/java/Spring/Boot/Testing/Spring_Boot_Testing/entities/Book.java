package Spring.Boot.Testing.Spring_Boot_Testing.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(nullable = false)
    private String title;
    @NotNull
    @Column(nullable = false)
    private String author;
    @NotNull
    @Column(nullable = false, unique = true)
    private String isbn;
    @Column(name = "published_year", nullable = false)
    private int publishedYear;
    @NotNull
    @Column(nullable = false)
    private String genre;
    @NotNull
    @Column(nullable = false)
    private BigDecimal price;
    @NotNull
    @Column(nullable = false)
    private Boolean available;
    @ElementCollection
    @CollectionTable(name = "book_rating", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "rating")
    private List<Integer> ratings;


    public Double countAvgRating(){
        return this.getRatings().stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

}
