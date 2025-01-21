package com.library_management.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.library_management.config.CustomResponse;
import com.library_management.dao.UserDAO;
import com.library_management.utill.Utills;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    Utills utills;

    @Override
    public ResponseEntity<?> getAllBooks(HttpServletRequest req, HttpServletResponse res, String searchKey) {

        try {

            // Fetching all books along with user details
            String key = searchKey != null ? searchKey : null;
            List<Object[]> getBooks = userDAO.getAllBooks(key);

            // Create a list to store the formatted book data
            List<Map<String, Object>> formattedBooks = new ArrayList<>();

            // Loop through the fetched books and process each
            for (Object[] book : getBooks) {
                Map<String, Object> bookDetails = new HashMap<>();

                // Extract book fields
                String bookId = (String) book[0];
                String bookName = (String) book[1];
                String authorRes = (String) book[2];
                String description = (String) book[3];
                String userListJson = (String) book[4]; // The user details are in a JSON string

                // Safely parse the userDetails JSON string
                List<Map<String, Object>> userDetails = new ArrayList<>();
                if (userListJson != null && !userListJson.isEmpty()) {
                    try {
                        // Parse the JSON string into a list of user objects
                        userDetails = new ObjectMapper().readValue(userListJson,
                                TypeFactory.defaultInstance().constructCollectionType(List.class, Map.class));
                    } catch (JsonProcessingException e) {
                        // If JSON parsing fails, log the error and return an empty list
                        userDetails = new ArrayList<>();
                    }
                }

                // Add book details to the map
                bookDetails.put("id", bookId);
                bookDetails.put("bookName", bookName);
                bookDetails.put("author", authorRes);
                bookDetails.put("description", description);
                bookDetails.put("userDetails", userDetails); // Parsed user details (array)

                // Add the formatted book details to the response list
                formattedBooks.add(bookDetails);
            }

            CustomResponse<?> responseBody = new CustomResponse<>(formattedBooks, "SUCCESS",
                    HttpStatus.OK.value(),
                    req.getRequestURI(), LocalDateTime.now());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (Exception e) {

            String stackTrace = utills.getStackTraceAsString(e);

            CustomResponse<String> responseBody = new CustomResponse<>(stackTrace,
                    "BAD_REQUEST",
                    HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

    }

}
