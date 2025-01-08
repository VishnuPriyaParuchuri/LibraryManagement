package com.library_management.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.library_management.dto.UserInfoDTO;
import com.library_management.dto.UserServiceDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AdminService {

    ResponseEntity<?> uploadBooksData(HttpServletRequest req, HttpServletResponse res, MultipartFile file,
            UserInfoDTO userDetails);

    ResponseEntity<?> getStudentRole(HttpServletRequest req, HttpServletResponse res, int page, int size);

    ResponseEntity<?> updateUserById(HttpServletRequest req, HttpServletResponse res, String id,
            UserServiceDTO userServiceDTO, UserInfoDTO userDetails);

    ResponseEntity<?> deleteUser(HttpServletRequest req, HttpServletResponse res, String id);

    ResponseEntity<?> uploadUsersData(HttpServletRequest req, HttpServletResponse res, MultipartFile file, UserInfoDTO userDetails);

    ResponseEntity<?> fetchUserBooksByUserId(HttpServletRequest req, HttpServletResponse res, UserInfoDTO userDetails);

}
