package com.library_management.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library_management.config.CustomResponse;
import com.library_management.dao.UserDAO;
import com.library_management.entity.BookEntity;
import com.library_management.utill.Utills;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    Utills utills;

    @Override
    public ResponseEntity<?> getAllBooks(HttpServletRequest req, HttpServletResponse res) {

        try {

            List<BookEntity> getBooks = userDAO.getAllBooks();

            CustomResponse<?> responseBody = new CustomResponse<>(getBooks, "SUCCESS",
                    HttpStatus.OK.value(),
                    req.getRequestURI(), LocalDateTime.now());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (Exception e) {

            String stackTrace = utills.getStackTraceAsString(e);

            CustomResponse<String> responseBody = new CustomResponse<>(stackTrace,
                    "BAD_REQUEST",
                    HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

    }

}
