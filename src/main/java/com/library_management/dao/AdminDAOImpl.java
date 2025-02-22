package com.library_management.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.library_management.dto.BookServiceDTO;
import com.library_management.dto.UserBookViewDTO;
import com.library_management.dto.UserInfoDTO;
import com.library_management.dto.UserServiceDTO;
import com.library_management.entity.BookEntity;
import com.library_management.entity.StudentBookEntity;
import com.library_management.entity.UserEntity;
import com.library_management.repository.BookRepository;
import com.library_management.repository.StudentBookRepo;
import com.library_management.repository.UserRepository;
import com.library_management.utill.UtillDTO;

@Service
public class AdminDAOImpl implements AdminDAO {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentBookRepo studentBookRepo;

    @Autowired
    UtillDTO utillDTO;

    @Override
    public List<BookEntity> uploadBooks(List<BookEntity> books) {

        if (books != null && !books.isEmpty()) {
            // Save the users to the database
            List<BookEntity> savedUsers = bookRepository.saveAll(books);
            return savedUsers; // Return the saved users list
        } else {
            // If the list is empty or null, you can handle accordingly (e.g., return an
            // empty list)
            return new ArrayList<>();
        }

    }

    @Override
    public List<BookEntity> getExisitingBooks(List<String> bookNames) {
        // TODO Auto-generated method stub

        List<BookEntity> users = bookRepository.findByBookNameIn(bookNames);

        return users;

    }

    @Override
    public Page<UserEntity> getStudentRole(String role, Pageable pageable) {
        // TODO Auto-generated method stub
        Page<UserEntity> user = userRepository.findByRole(role, pageable);
        if (!user.isEmpty()) {
            return user;
        } else {
            return Page.empty();
        }
    }

    @Override
    public UserServiceDTO updateUserInfo(String id, UserServiceDTO userServiceDTO, UserInfoDTO userDetails) {

        return userRepository.findById(id)
                .map(entity -> {
                    // Update other fields as needed
                    if (userServiceDTO.getUserName() != null) {
                        entity.setUserName(userServiceDTO.getUserName());
                    }

                    if (userServiceDTO.getEmail() != null) {
                        entity.setEmail(userServiceDTO.getEmail());
                    }

                    if (userServiceDTO.getPhone() != null) {
                        entity.setPhone(userServiceDTO.getPhone());
                    }

                    if (userServiceDTO.getCountry() != null) {
                        entity.setCountry(userServiceDTO.getCountry());
                    }

                    if (userServiceDTO.getState() != null) {
                        entity.setState(userServiceDTO.getState());
                    }

                    if (userServiceDTO.getDob() != null) {
                        entity.setDob(userServiceDTO.getDob());
                    }

                    if (userServiceDTO.getGender() != null) {
                        entity.setGender(userServiceDTO.getGender());
                    }

                    entity.setUpdatedAt(LocalDateTime.now());
                    entity.setUpdatedBy(userDetails.getUuid());

                    UserEntity userEntity = userRepository.save(entity);
                    return utillDTO.convertToUserDTO(userEntity);

                }).orElseThrow(() -> new UsernameNotFoundException("User not found with id "
                + id));
    }

    @Override
    public Optional<UserEntity> deleteUserInfo(String id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            // Delete the user entity
            userRepository.deleteById(id);
            return user;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<UserEntity> getExisitingUsers(List<String> emails) {

        List<UserEntity> users = userRepository.findUsersByEmails(emails);

        return users;

    }

    @Override
    public List<UserEntity> uploadUserInfo(List<UserEntity> users) {

        if (users != null && !users.isEmpty()) {
            // Save the users to the database
            List<UserEntity> savedUsers = userRepository.saveAll(users);
            return savedUsers; // Return the saved users list
        } else {
            // If the list is empty or null, you can handle accordingly (e.g., return an
            // empty list)
            return new ArrayList<>();
        }
    }

    @Override
    public List<UserBookViewDTO> getUserBooksById(String id) {
        // TODO Auto-generated method stub

        return userRepository.findUserBooksById(id);
    }

    @Override
    public Page<UserBookViewDTO> getUserBooksInfoByBookId(String id, Pageable pageable) {
        // TODO Auto-generated method stub

        return userRepository.findUserBooksByBookId(id, pageable);
    }

    @Override
    public BookServiceDTO updateBooksInfo(String id, BookServiceDTO bookServiceDTO) {
        // TODO Auto-generated method stub

        return bookRepository.findById(id)
                .map(entity -> {
                    // Update other fields as needed
                    if (bookServiceDTO.getBookName() != null) {
                        entity.setBookName(bookServiceDTO.getBookName());
                    }

                    if (bookServiceDTO.getAuthor() != null) {
                        entity.setAuthor(bookServiceDTO.getAuthor());
                    }

                    if (bookServiceDTO.getDescription() != null) {
                        entity.setDescription(bookServiceDTO.getDescription());
                    }

                    if (bookServiceDTO.getNoOfSets() != null) {
                        entity.setNoOfSets(bookServiceDTO.getNoOfSets());
                    }

                    entity.setUpdatedAt(LocalDateTime.now());
                    // entity.setUpdatedBy(userDetails.getUuid());

                    BookEntity bookEntity = bookRepository.save(entity);
                    return utillDTO.convertToBookDTO(bookEntity);

                }).orElseThrow(() -> new UsernameNotFoundException("User not found with id "
                + id));
    }

    @Override
    public Optional<BookEntity> deleteBookInfo(String id) {
        // TODO Auto-generated method stub

        Optional<BookEntity> deleteBook = bookRepository.findById(id);
        if (deleteBook.isPresent()) {
            bookRepository.deleteById(id);
            return deleteBook;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<BookEntity> getBookById(String bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public Optional<UserEntity> getUserByRollNumber(String rollnumber) {
        return userRepository.findByRollNo(rollnumber);
    }

    @Override
    public BookEntity updateBookDetails(BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }

    @Override
    public StudentBookEntity createStudentBook(StudentBookEntity studentBook) {
        return studentBookRepo.save(studentBook);
    }

    @Override
    public Optional<StudentBookEntity> checkBookAssigned(String bookId, String userId) {
        return studentBookRepo.findByBookIdAndUserId(bookId, userId);
    }

}
