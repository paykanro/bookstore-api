package com.example.bookstore.repository;

import com.example.bookstore.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * interface: قرارداد که کلاس‌های پیاده‌کننده باید متدهای آن را implement کنند
 * نکته: Spring Data JPA به صورت خودکار implementation ایجاد می‌کند
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // polymorphism: اینترفیس می‌تواند multiple inheritance داشته باشد

    // derived query - Spring Data JPA به صورت خودکار کوئری می‌سازد
    Optional<Author> findByEmail(String email);

    List<Author> findByLastNameContainingIgnoreCase(String lastName);

    List<Author> findByAgeGreaterThanEqual(Integer age);

    // JPQL query
    @Query("SELECT a FROM Author a WHERE SIZE(a.books) >= :minBooks")
    List<Author> findAuthorsWithMinimumBooks(int minBooks);

    // native query
    @Query(value = "SELECT * FROM authors a WHERE a.first_name LIKE %:name% OR a.last_name LIKE %:name%",
            nativeQuery = true)
    List<Author> findByNameContaining(String name);
}