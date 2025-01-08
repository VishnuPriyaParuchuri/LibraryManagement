package com.library_management.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Size;

public class BookServiceDTO {

    private String id;

    @Size(min = 2, max = 50, message = "Bookname must be between 2 and 50 characters")
    private String bookName;

    private String author;

    private String description;

    private Integer noOfSets;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    public BookServiceDTO() {

    }

    public BookServiceDTO(String id, String bookName,
            String author, String description, Integer noOfSets, LocalDateTime createdAt, String createdBy,
            LocalDateTime updatedAt, String updatedBy) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.description = description;
        this.noOfSets = noOfSets;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNoOfSets() {
        return noOfSets;
    }

    public void setNoOfSets(Integer noOfSets) {
        this.noOfSets = noOfSets;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

}
