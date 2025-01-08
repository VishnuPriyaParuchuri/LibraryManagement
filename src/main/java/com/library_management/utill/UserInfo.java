package com.library_management.utill;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.library_management.dao.RegistrationDAO;
import com.library_management.dto.UserInfoDTO;
import com.library_management.entity.UserEntity;


@Component
public class UserInfo implements UserDetailsService {

    @Autowired
    RegistrationDAO registrationDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> existingUser = registrationDAO.isUserExists(username);

        System.out.println("existingUser" + " " + existingUser.toString());

        if (existingUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new UserInfoDTO(existingUser.get());
    }

}
