package com.library_management.dto;

import java.time.LocalDateTime;

public class UserBookViewDTO {

    private String id;

    private String user_name;

    private String email;

    private String author;

    private String book_name;

    private String description;

    private Integer no_of_sets;

    private LocalDateTime submission_date;

    private String status;

    private String bookId;

    public UserBookViewDTO(String id, String user_name, String email, String author, String book_name,
            String description, Integer no_of_sets, LocalDateTime submission_date, String status, String bookId) {
        this.id = id;
        this.user_name = user_name;
        this.email = email;
        this.author = author;
        this.book_name = book_name;
        this.description = description;
        this.no_of_sets = no_of_sets;
        this.submission_date = submission_date;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNo_of_sets() {
        return no_of_sets;
    }

    public void setNo_of_sets(Integer no_of_sets) {
        this.no_of_sets = no_of_sets;
    }

    public LocalDateTime getSubmission_date() {
        return submission_date;
    }

    public void setSubmission_date(LocalDateTime submission_date) {
        this.submission_date = submission_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

}
