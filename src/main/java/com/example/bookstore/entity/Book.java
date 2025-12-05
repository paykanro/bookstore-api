package com.example.bookstore.entity;

import com.example.bookstore.enums.BookStatus;
import com.example.bookstore.enums.BookCategory;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * inheritance: Book از BaseEntity ارث‌بری می‌کند
 * نکته: تمام فیلدها و متدهای BaseEntity در Book موجود است
 */
@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @NotBlank(message = "عنوان کتاب نمی‌تواند خالی باشد")
    @Size(min = 1, max = 255, message = "عنوان کتاب باید بین ۱ تا ۲۵۵ کاراکتر باشد")
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank(message = "شابک نمی‌تواند خالی باشد")
    @Pattern(regexp = "\\d{13}", message = "شابک باید ۱۳ رقم باشد")
    @Column(name = "isbn", unique = true, nullable = false, length = 13)
    private String isbn;

    @NotNull(message = "دسته‌بندی کتاب باید مشخص شود")
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private BookCategory category;

    @NotNull(message = "وضعیت کتاب باید مشخص شود")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus status = BookStatus.AVAILABLE; // مقدار پیش‌فرض

    @DecimalMin(value = "0.0", message = "قیمت نمی‌تواند منفی باشد")
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Min(value = 0, message = "تعداد صفحات نمی‌تواند منفی باشد")
    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    // رابطه Many-to-One با Author
    @NotNull(message = "نویسنده باید مشخص شود")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    // Constructors
    public Book() {
        // default constructor
    }

    public Book(String title, String isbn, BookCategory category, Author author) {
        this.title = title;
        this.isbn = isbn;
        this.category = category;
        this.author = author;
    }

    // Getter and Setter methods
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    // متدهای business logic
    public boolean isAvailable() {
        return this.status == BookStatus.AVAILABLE;
    }

    public void borrowBook() {
        if (isAvailable()) {
            this.status = BookStatus.BORROWED;
        }
    }

    public void returnBook() {
        this.status = BookStatus.AVAILABLE;
    }

    // polymorphism: پیاده‌سازی متد abstract والد با منطق خاص Book
    @Override
    public String getDisplayName() {
        return "کتاب: " + title + " (" + category.getPersianName() + ")";
    }

    // overloading: متد با نام یکسان اما پارامترهای مختلف
    public void updatePrice(BigDecimal newPrice) {
        this.price = newPrice;
    }

    public void updatePrice(double newPrice) {
        this.price = BigDecimal.valueOf(newPrice);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", category=" + category +
                ", status=" + status +
                ", price=" + price +
                ", author=" + (author != null ? author.getFullName() : "null") +
                '}';
    }
}