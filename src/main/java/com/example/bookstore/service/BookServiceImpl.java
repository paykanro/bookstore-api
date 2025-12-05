package com.example.bookstore.service;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import com.example.bookstore.enums.BookCategory;
import com.example.bookstore.enums.BookStatus;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("کتاب با ID " + id + " یافت نشد"));
        return convertToDTO(book);
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        // بررسی وجود ISBN تکراری
        bookRepository.findByIsbn(bookDTO.getIsbn())
                .ifPresent(book -> {
                    throw new IllegalArgumentException("کتاب با شابک " + bookDTO.getIsbn() + " از قبل وجود دارد");
                });

        // بررسی وجود author
        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "نویسنده با ID " + bookDTO.getAuthorId() + " یافت نشد"));

        Book book = convertToEntity(bookDTO);
        book.setAuthor(author);

        Book savedBook = bookRepository.save(book);
        return convertToDTO(savedBook);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("کتاب با ID " + id + " یافت نشد"));

        // بررسی تغییر ISBN (اگر تغییر کرده و تکراری است)
        if (!existingBook.getIsbn().equals(bookDTO.getIsbn())) {
            bookRepository.findByIsbn(bookDTO.getIsbn())
                    .ifPresent(book -> {
                        throw new IllegalArgumentException("کتاب با شابک " + bookDTO.getIsbn() + " از قبل وجود دارد");
                    });
        }

        // به روزرسانی فیلدها
        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setCategory(bookDTO.getCategory());
        existingBook.setPrice(bookDTO.getPrice());
        existingBook.setPageCount(bookDTO.getPageCount());
        existingBook.setPublicationDate(bookDTO.getPublicationDate());

        // اگر author تغییر کرده
        if (!existingBook.getAuthor().getId().equals(bookDTO.getAuthorId())) {
            Author newAuthor = authorRepository.findById(bookDTO.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "نویسنده با ID " + bookDTO.getAuthorId() + " یافت نشد"));
            existingBook.setAuthor(newAuthor);
        }

        Book updatedBook = bookRepository.save(existingBook);
        return convertToDTO(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("کتاب با ID " + id + " یافت نشد"));
        bookRepository.delete(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getBooksByCategory(BookCategory category) {
        return bookRepository.findByCategory(category)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getBooksByStatus(BookStatus status) {
        return bookRepository.findByStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getBooksByAuthor(Long authorId) {
        // بررسی وجود author
        authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("نویسنده با ID " + authorId + " یافت نشد"));

        return bookRepository.findByAuthorId(authorId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO borrowBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("کتاب با ID " + id + " یافت نشد"));

        if (!book.isAvailable()) {
            throw new IllegalStateException("کتاب برای امانت دادن موجود نیست. وضعیت فعلی: " +
                    book.getStatus().getStatusDescription());
        }

        book.borrowBook();
        Book updatedBook = bookRepository.save(book);
        return convertToDTO(updatedBook);
    }

    @Override
    public BookDTO returnBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("کتاب با ID " + id + " یافت نشد"));

        book.returnBook();
        Book updatedBook = bookRepository.save(book);
        return convertToDTO(updatedBook);
    }

    @Override
    public BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setCategory(book.getCategory());
        dto.setStatus(book.getStatus());
        dto.setPrice(book.getPrice());
        dto.setPageCount(book.getPageCount());
        dto.setPublicationDate(book.getPublicationDate());

        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getId());
            dto.setAuthorName(book.getAuthor().getFullName());
        }

        return dto;
    }

    @Override
    public Book convertToEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setCategory(bookDTO.getCategory());
        book.setStatus(bookDTO.getStatus() != null ? bookDTO.getStatus() : BookStatus.AVAILABLE);
        book.setPrice(bookDTO.getPrice());
        book.setPageCount(bookDTO.getPageCount());
        book.setPublicationDate(bookDTO.getPublicationDate());
        return book;
    }
}