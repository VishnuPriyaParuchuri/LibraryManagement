package com.library_management.dto;

import java.util.List;

public class BookWithUsersDTO {

    private String id;

    private String bookName;

    private String author;

    private String description;

    private List<UserServiceDTO> userList;

    public BookWithUsersDTO() {

    }

    public BookWithUsersDTO(String id, String bookName, String author, String description,
            List<UserServiceDTO> userList) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.description = description;
        this.userList = userList;
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

    public List<UserServiceDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<UserServiceDTO> userList) {
        this.userList = userList;
    }

}