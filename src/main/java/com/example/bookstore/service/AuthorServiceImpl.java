package com.example.bookstore.service;

import com.example.bookstore.dto.AuthorDTO;
import com.example.bookstore.entity.Author;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * service implementation: پیاده‌سازی business logic
 * نکته: استفاده از @Service برای مشخص کردن کلاس به عنوان service bean
 */
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    // dependency injection - Spring به صورت خودکار وابستگی را تزریق می‌کند
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    // constructor injection - recommended approach
    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDTO> getAllAuthors() {
        // stream API - برای پردازش مجموعه‌ها
        return authorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDTO getAuthorById(Long id) {
        // optional: برای مدیریت مقادیر null به صورت ایمن
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("نویسنده با ID " + id + " یافت نشد"));
        return convertToDTO(author);
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        // validation logic
        if (authorDTO.getEmail() != null) {
            authorRepository.findByEmail(authorDTO.getEmail())
                    .ifPresent(author -> {
                        throw new IllegalArgumentException("نویسنده با ایمیل " + authorDTO.getEmail() + " از قبل وجود دارد");
                    });
        }

        Author author = convertToEntity(authorDTO);
        Author savedAuthor = authorRepository.save(author);
        return convertToDTO(savedAuthor);
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        // پیدا کردن author موجود
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("نویسنده با ID " + id + " یافت نشد"));

        // به روزرسانی فیلدها
        existingAuthor.setFirstName(authorDTO.getFirstName());
        existingAuthor.setLastName(authorDTO.getLastName());
        existingAuthor.setEmail(authorDTO.getEmail());
        existingAuthor.setAge(authorDTO.getAge());

        Author updatedAuthor = authorRepository.save(existingAuthor);
        return convertToDTO(updatedAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {
        // بررسی وجود author
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("نویسنده با ID " + id + " یافت نشد"));

        // بررسی اینکه author کتابی نداشته باشد
        Long bookCount = bookRepository.countBooksByAuthor(id);
        if (bookCount > 0) {
            throw new IllegalStateException("نمی‌توان نویسنده‌ای که کتاب دارد را حذف کرد. تعداد کتاب‌ها: " + bookCount);
        }

        authorRepository.delete(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDTO> searchAuthorsByName(String name) {
        return authorRepository.findByNameContaining(name)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDTO> getAuthorsWithMinimumBooks(int minBooks) {
        return authorRepository.findAuthorsWithMinimumBooks(minBooks)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDTO getAuthorByEmail(String email) {
        Author author = authorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("نویسنده با ایمیل " + email + " یافت نشد"));
        return convertToDTO(author);
    }

    @Override
    public AuthorDTO convertToDTO(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setFirstName(author.getFirstName());
        dto.setLastName(author.getLastName());
        dto.setEmail(author.getEmail());
        dto.setAge(author.getAge());

        // محاسبه تعداد کتاب‌ها
        if (author.getBooks() != null) {
            dto.setBookCount(author.getBooks().size());
        } else {
            dto.setBookCount(0);
        }

        return dto;
    }

    @Override
    public Author convertToEntity(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setEmail(authorDTO.getEmail());
        author.setAge(authorDTO.getAge());
        return author;
    }
}