package com.library_management.dao;

import java.util.List;
import java.util.Optional;

import com.library_management.entity.BookEntity;

public interface UserDAO {

    List<BookEntity> getAllBooks();

}
