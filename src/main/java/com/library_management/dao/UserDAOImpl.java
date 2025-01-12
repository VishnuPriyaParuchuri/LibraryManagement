package com.library_management.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library_management.repository.BookRepository;

@Service
public class UserDAOImpl implements UserDAO {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Object[]> getAllBooks(String searchKey) {
        // TODO Auto-generated method stub

        return bookRepository.findBooksWithUserDetails(searchKey);

    }

}
