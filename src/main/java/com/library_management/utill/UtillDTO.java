package com.library_management.utill;


import org.springframework.stereotype.Component;

import com.library_management.dto.BookServiceDTO;
import com.library_management.dto.UserServiceDTO;
import com.library_management.entity.BookEntity;
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

    public BookServiceDTO convertToBookDTO(BookEntity bookEntity) {
        return new BookServiceDTO(
                bookEntity.getId(),
                bookEntity.getBookName(),
                bookEntity.getAuthor(),
                bookEntity.getDescription(),
                bookEntity.getNoOfSets(),
                bookEntity.getCreatedAt(),
                bookEntity.getCreatedBy(),
                bookEntity.getUpdatedAt(),
                bookEntity.getUpdatedBy());
    }

}
