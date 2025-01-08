package com.library_management.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library_management.entity.UserEntity;
import com.library_management.repository.UserRepository;
import com.library_management.utill.Utills;

@Service
public class RegistrationDAOImpl implements RegistrationDAO {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utills utill;

    @Override
    public Optional<UserEntity> isUserExists(String email) {
        // TODO Auto-generated method stub
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity createUser(UserEntity userDetails) {
        // TODO Auto-generated method stub
        return userRepository.save(userDetails);
    }

}
