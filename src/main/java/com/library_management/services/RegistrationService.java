package com.library_management.services;

import org.springframework.http.ResponseEntity;

import com.library_management.dto.UserServiceDTO;
import com.library_management.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

public interface RegistrationService {

    ResponseEntity<?> createUserInfo(@Valid UserServiceDTO userServiceDTO, HttpServletRequest req,
            HttpServletResponse res);

    ResponseEntity<?> athunticateUser(UserEntity userEntity, HttpServletRequest req, HttpServletResponse res);

}
