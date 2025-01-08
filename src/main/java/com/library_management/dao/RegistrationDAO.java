package com.library_management.dao;

import java.util.Optional;

import com.library_management.entity.UserEntity;

public interface RegistrationDAO {

    Optional<UserEntity> isUserExists(String email);

    UserEntity createUser(UserEntity userDetails);

}
