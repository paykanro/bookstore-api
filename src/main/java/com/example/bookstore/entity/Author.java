package com.example.bookstore.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class: blueprint برای ایجاد objectها
 * نکته: هر Author یک object از این کلاس خواهد بود
 */
@Entity
@Table(name = "authors")
public class Author extends BaseEntity {

    // instance variables با validation annotations
    @NotBlank(message = "نام نویسنده نمی‌تواند خالی باشد")
    @Size(min = 2, max = 100, message = "نام نویسنده باید بین ۲ تا ۱۰۰ کاراکتر باشد")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "نام خانوادگی نویسنده نمی‌تواند خالی باشد")
    @Size(min = 2, max = 100, message = "نام خانوادگی نویسنده باید بین ۲ تا ۱۰۰ کاراکتر باشد")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email(message = "ایمیل باید معتبر باشد")
    @Column(name = "email", unique = true)
    private String email;

    @Min(value = 0, message = "سن نمی‌تواند منفی باشد")
    @Max(value = 120, message = "سن نمی‌تواند بیشتر از ۱۲۰ باشد")
    @Column(name = "age")
    private Integer age;

    // رابطه One-to-Many با Book
    // List interface: polymorphism - می‌تواند ArrayList یا LinkedList باشد
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    // Constructors - نمونه‌ای از overloading (همان متد با پارامترهای مختلف)
    public Author() {
        // default constructor - برای JPA لازم است
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Author(String firstName, String lastName, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    // Getter and Setter methods
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    // متدهای business logic
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setAuthor(null);
    }

    // override: پیاده‌سازی متد abstract والد
    @Override
    public String getDisplayName() {
        return "نویسنده: " + getFullName();
    }

    // toString method - برای نمایش object
    @Override
    public String toString() {
        return "Author{" +
                "id=" + getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}