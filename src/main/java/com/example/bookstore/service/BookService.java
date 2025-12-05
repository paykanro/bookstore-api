package com.example.bookstore.service;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.enums.BookCategory;
import com.example.bookstore.enums.BookStatus;

import java.util.List;

public interface BookService {

    // CRUD operations
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    BookDTO createBook(BookDTO bookDTO);
    BookDTO updateBook(Long id, BookDTO bookDTO);
    void deleteBook(Long id);

    // Business operations
    List<BookDTO> getBooksByCategory(BookCategory category);
    List<BookDTO> getBooksByStatus(BookStatus status);
    List<BookDTO> searchBooksByTitle(String title);
    List<BookDTO> getBooksByAuthor(Long authorId);
    BookDTO borrowBook(Long id);
    BookDTO returnBook(Long id);

    // Utility methods
    BookDTO convertToDTO(com.example.bookstore.entity.Book book);
    com.example.bookstore.entity.Book convertToEntity(BookDTO bookDTO);
}