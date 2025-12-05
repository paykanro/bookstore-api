package com.example.bookstore.service;

import com.example.bookstore.dto.AuthorDTO;
import com.example.bookstore.entity.Author;

import java.util.List;

/**
 * service interface: قرارداد برای business logic
 * نکته: استفاده از interface برای loose coupling و تست‌پذیری
 */
public interface AuthorService {

    // CRUD operations
    List<AuthorDTO> getAllAuthors();
    AuthorDTO getAuthorById(Long id);
    AuthorDTO createAuthor(AuthorDTO authorDTO);
    AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO);
    void deleteAuthor(Long id);

    // Business operations
    List<AuthorDTO> searchAuthorsByName(String name);
    List<AuthorDTO> getAuthorsWithMinimumBooks(int minBooks);
    AuthorDTO getAuthorByEmail(String email);

    // Utility method برای تبدیل entity به DTO
    AuthorDTO convertToDTO(Author author);
    Author convertToEntity(AuthorDTO authorDTO);
}