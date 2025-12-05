package com.example.bookstore.dto;

import javax.validation.constraints.*;

public class AuthorDTO {

    private Long id;

    @NotBlank(message = "نام نویسنده نمی‌تواند خالی باشد")
    private String firstName;

    @NotBlank(message = "نام خانوادگی نویسنده نمی‌تواند خالی باشد")
    private String lastName;

    @Email(message = "ایمیل باید معتبر باشد")
    private String email;

    @Min(value = 0, message = "سن نمی‌تواند منفی باشد")
    @Max(value = 120, message = "سن نمی‌تواند بیشتر از ۱۲۰ باشد")
    private Integer age;

    private Integer bookCount;

    // Constructors
    public AuthorDTO() {}

    public AuthorDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getter and Setter methods
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Integer getBookCount() { return bookCount; }
    public void setBookCount(Integer bookCount) { this.bookCount = bookCount; }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}