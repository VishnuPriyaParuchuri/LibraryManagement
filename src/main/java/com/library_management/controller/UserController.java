package com.library_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library_management.entity.BookEntity;
import com.library_management.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v2")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/dashboard")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String home(HttpServletRequest req,
            HttpServletResponse res) {
        return "Welcome to user dashboard";
    }

    @GetMapping("/get/all/books")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> getAllBokks(HttpServletRequest req, HttpServletResponse res) {

        return userService.getAllBooks(req, res);
    }
}
