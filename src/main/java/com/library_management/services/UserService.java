package com.library_management.services;

import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    ResponseEntity<?> getAllBooks(HttpServletRequest req, HttpServletResponse res, String searchKey);

}
