package com.library_management.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.library_management.dto.BookServiceDTO;
import com.library_management.dto.UserBookViewDTO;
import com.library_management.dto.UserInfoDTO;
import com.library_management.dto.UserServiceDTO;
import com.library_management.entity.BookEntity;
import com.library_management.entity.UserEntity;

public interface AdminDAO {

    List<BookEntity> uploadBooks(List<BookEntity> books);

    List<BookEntity> getExisitingBooks(List<String> bookNames);

    Page<UserEntity> getStudentRole(String role, Pageable pageable);

    UserServiceDTO updateUserInfo(String id, UserServiceDTO userServiceDTO, UserInfoDTO userDetails);

    Optional<UserEntity> deleteUserInfo(String id);

    List<UserEntity> getExisitingUsers(List<String> emails);

    List<UserEntity> uploadUserInfo(List<UserEntity> users);

    List<UserBookViewDTO> getUserBooksById(String id);

    Page<UserBookViewDTO> getUserBooksInfoByBookId(String id, Pageable pageable);

    BookServiceDTO updateBooksInfo(String id, BookServiceDTO bookServiceDTO);

    Optional<BookEntity> deleteBookInfo(String id);

}
