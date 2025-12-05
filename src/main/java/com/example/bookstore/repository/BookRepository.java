package com.example.bookstore.repository;

import com.example.bookstore.entity.Book;
import com.example.bookstore.enums.BookCategory;
import com.example.bookstore.enums.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // derived queries
    Optional<Book> findByIsbn(String isbn);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByCategory(BookCategory category);

    List<Book> findByStatus(BookStatus status);

    List<Book> findByAuthorId(Long authorId);

    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);

    // JPQL با join
    @Query("SELECT b FROM Book b JOIN b.author a WHERE a.id = :authorId AND b.category = :category")
    List<Book> findByAuthorAndCategory(Long authorId, BookCategory category);

    @Query("SELECT COUNT(b) FROM Book b WHERE b.author.id = :authorId")
    Long countBooksByAuthor(Long authorId);

    // projection interface - نمایش polymorphism
    interface BookSummary {
        String getTitle();
        String getIsbn();
        BookStatus getStatus();
        String getAuthorFirstName();
        String getAuthorLastName();
    }

    @Query("SELECT b.title as title, b.isbn as isbn, b.status as status, " +
            "a.firstName as authorFirstName, a.lastName as authorLastName " +
            "FROM Book b JOIN b.author a WHERE b.category = :category")
    List<BookSummary> findBookSummariesByCategory(BookCategory category);
}