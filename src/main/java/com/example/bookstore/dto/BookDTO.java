package com.example.bookstore.dto;

import com.example.bookstore.enums.BookCategory;
import com.example.bookstore.enums.BookStatus;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO Pattern: برای انتقال داده بین لایه‌ها بدون در معرض قرار دادن entityها
 */
public class BookDTO {

    private Long id;

    @NotBlank(message = "عنوان کتاب نمی‌تواند خالی باشد")
    private String title;

    @NotBlank(message = "شابک نمی‌تواند خالی باشد")
    @Pattern(regexp = "\\d{13}", message = "شابک باید ۱۳ رقم باشد")
    private String isbn;

    @NotNull(message = "دسته‌بندی کتاب باید مشخص شود")
    private BookCategory category;

    private BookStatus status;

    @DecimalMin(value = "0.0", message = "قیمت نمی‌تواند منفی باشد")
    private BigDecimal price;

    @Min(value = 0, message = "تعداد صفحات نمی‌تواند منفی باشد")
    private Integer pageCount;

    private LocalDate publicationDate;

    @NotNull(message = "شناسه نویسنده باید مشخص شود")
    private Long authorId;

    private String authorName;

    // Constructors
    public BookDTO() {}

    public BookDTO(String title, String isbn, BookCategory category, Long authorId) {
        this.title = title;
        this.isbn = isbn;
        this.category = category;
        this.authorId = authorId;
    }

    // Getter and Setter methods
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public BookCategory getCategory() { return category; }
    public void setCategory(BookCategory category) { this.category = category; }

    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getPageCount() { return pageCount; }
    public void setPageCount(Integer pageCount) { this.pageCount = pageCount; }

    public LocalDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
}