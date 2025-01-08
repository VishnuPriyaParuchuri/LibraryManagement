package com.library_management.utill;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.library_management.dto.UserServiceDTO;
import com.library_management.entity.UserEntity;

@Component
public class UtillDTO {

    public UserServiceDTO convertToUserDTO(UserEntity userEntity) {
        return new UserServiceDTO(
                userEntity.getId(),
                userEntity.getUserName(),
                userEntity.getEmail(),
                userEntity.getRole(),
                userEntity.getCountry(),
                userEntity.getState(),
                userEntity.getDob(),
                userEntity.getPhone(),
                userEntity.getRollNo(),
                userEntity.getGender(),
                userEntity.getCreatedAt(),
                userEntity.getCreatedBy(),
                userEntity.getUpdatedAt(),
                userEntity.getUpdatedBy());
    }

}
