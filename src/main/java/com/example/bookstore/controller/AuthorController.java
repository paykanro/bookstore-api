package com.example.bookstore.controller;

import com.example.bookstore.dto.AuthorDTO;
import com.example.bookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST Controller: مدیریت درخواست‌های HTTP
 * نکته: استفاده از annotationهای Spring برای mapping درخواست‌ها
 */
@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "*") // برای توسعه front-end
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // GET /api/authors - دریافت همه نویسندگان
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    // GET /api/authors/{id} - دریافت نویسنده بر اساس ID
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        AuthorDTO author = authorService.getAuthorById(id);
        return ResponseEntity.ok(author);
    }

    // POST /api/authors - ایجاد نویسنده جدید
    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        AuthorDTO createdAuthor = authorService.createAuthor(authorDTO);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    // PUT /api/authors/{id} - به روزرسانی نویسنده
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorDTO authorDTO) {
        AuthorDTO updatedAuthor = authorService.updateAuthor(id, authorDTO);
        return ResponseEntity.ok(updatedAuthor);
    }

    // DELETE /api/authors/{id} - حذف نویسنده
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/authors/search - جستجوی نویسندگان بر اساس نام
    @GetMapping("/search")
    public ResponseEntity<List<AuthorDTO>> searchAuthors(@RequestParam String name) {
        List<AuthorDTO> authors = authorService.searchAuthorsByName(name);
        return ResponseEntity.ok(authors);
    }

    // GET /api/authors/with-min-books - نویسندگان با حداقل تعداد کتاب
    @GetMapping("/with-min-books")
    public ResponseEntity<List<AuthorDTO>> getAuthorsWithMinimumBooks(
            @RequestParam(defaultValue = "1") int minBooks) {
        List<AuthorDTO> authors = authorService.getAuthorsWithMinimumBooks(minBooks);
        return ResponseEntity.ok(authors);
    }

    // GET /api/authors/email/{email} - دریافت نویسنده بر اساس ایمیل
    @GetMapping("/email/{email}")
    public ResponseEntity<AuthorDTO> getAuthorByEmail(@PathVariable String email) {
        AuthorDTO author = authorService.getAuthorByEmail(email);
        return ResponseEntity.ok(author);
    }
}