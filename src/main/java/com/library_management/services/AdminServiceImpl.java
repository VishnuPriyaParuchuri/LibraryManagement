package com.library_management.services;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.library_management.config.CustomResponse;
import com.library_management.dao.AdminDAO;
import com.library_management.dto.UserBookViewDTO;
import com.library_management.dto.UserInfoDTO;
import com.library_management.dto.UserServiceDTO;
import com.library_management.entity.BookEntity;
import com.library_management.entity.UserEntity;
import com.library_management.utill.Utills;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDAO adminDAO;

    @Autowired
    Utills utills;

    @Override
    public ResponseEntity<?> uploadBooksData(HttpServletRequest req, HttpServletResponse res, MultipartFile file,
            UserInfoDTO userDetails) {
        // TODO Auto-generated method stub
        try {
            if (file.isEmpty()) {

                String errorMessage = "File is Empty !";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessage, "NOT_FOUND",
                        HttpStatus.NOT_FOUND.value(), req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);

            }

            if (!file.getOriginalFilename().endsWith(".xls") && !file.getOriginalFilename().endsWith(".xlsx")) {
                String errorMessage = "Invalid file type! Please upload a valid Excel file.";
                CustomResponse<String> responseBody = new CustomResponse<>(errorMessage, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            List<BookEntity> books = new ArrayList<>();
            try {
                Workbook workbook = new XSSFWorkbook(file.getInputStream());
                Sheet sheet = workbook.getSheetAt(0);
                Row headerRow = sheet.getRow(0);

                validateBookHeaders(headerRow);

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (rowEmpty(row)) {
                        continue;
                    }
                    BookEntity book = validateAndParseBookRow(row, userDetails);
                    books.add(book);
                }
            } catch (Exception e) {
                CustomResponse<String> responseBody = new CustomResponse<>(e.getMessage(), "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            List<BookEntity> booksList = adminDAO.uploadBooks(books);

            CustomResponse<?> responseBody = new CustomResponse<>(booksList, "SUCCESS", HttpStatus.OK.value(),
                    req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        } catch (Exception e) {
            CustomResponse<String> responseBody = new CustomResponse<>(e.getMessage(), "BAD_REQUEST",
                    HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    private void validateBookHeaders(Row headerRow) {
        List<String> requiredHeaders = List.of("bookName", "author", "description");
        for (int i = 0; i < requiredHeaders.size(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell == null || !requiredHeaders.get(i).equalsIgnoreCase(cell.getStringCellValue().trim())) {
                throw new IllegalArgumentException("Invalid column name: " + requiredHeaders.get(i));
            }
        }
    }

    private boolean rowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (Cell cell : row) {
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String cellValue = cell.toString().trim();
                if (!cellValue.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private BookEntity validateAndParseBookRow(Row row, UserInfoDTO userDetails) {
        BookEntity book = new BookEntity();

        // Read "bookName" (column 0)
        Cell bookNameCell = row.getCell(0);
        if (bookNameCell != null && bookNameCell.getCellType() == CellType.STRING) {
            book.setBookName(bookNameCell.getStringCellValue().trim());
        } else {
            throw new IllegalArgumentException("Please enter the field bookName at row " + (row.getRowNum() + 1));
        }

        // Read "author" (column 1)
        Cell authorCell = row.getCell(1);
        if (authorCell != null && authorCell.getCellType() == CellType.STRING) {
            book.setAuthor(authorCell.getStringCellValue().trim());
        } else {
            throw new IllegalArgumentException("Please enter the field author at row " + (row.getRowNum() + 1));
        }

        // Read "description" (column 2)
        Cell descriptionCell = row.getCell(2);
        if (descriptionCell != null && descriptionCell.getCellType() == CellType.STRING) {
            book.setDescription(descriptionCell.getStringCellValue().trim());
        } else {
            throw new IllegalArgumentException("Please enter the field description at row " + (row.getRowNum() + 1));
        }

        Cell noOFSetsCell = row.getCell(3);
        if (noOFSetsCell != null && noOFSetsCell.getCellType() == CellType.NUMERIC) {
            book.setNoOfSets((int) noOFSetsCell.getNumericCellValue());
        } else {
            throw new IllegalArgumentException("Please enter the field no of sets at row " + (row.getRowNum() + 1));
        }

        book.setCreatedAt(LocalDateTime.now());
        book.setCreatedBy(userDetails.getUuid());

        return book;
    }

    @Override
    public ResponseEntity<?> getStudentRole(HttpServletRequest req, HttpServletResponse res, int page, int size) {
        try {

            String role = "ROLE_STUDENT,";

            Pageable pageable = PageRequest.of(page, size);

            Page<UserEntity> getStudentData = adminDAO.getStudentRole(role, pageable);

            Map<String, Object> finalUserList = new LinkedHashMap<>();
            finalUserList.put("users", getStudentData.getContent());
            finalUserList.put("currentPage", getStudentData.getNumber());
            finalUserList.put("totalItems", getStudentData.getTotalElements());
            finalUserList.put("totalPages", getStudentData.getTotalPages());

            CustomResponse<?> responseBody = new CustomResponse<>(finalUserList, "SUCCESS",
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

    @Override
    public ResponseEntity<?> updateUserById(HttpServletRequest req, HttpServletResponse res, String id,
            UserServiceDTO userServiceDTO, UserInfoDTO userDetails) {

        try {

            if (id.isBlank()) {

                String errorMessages = "Id is required!";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            UserServiceDTO updateUser = adminDAO.updateUserInfo(id, userServiceDTO, userDetails);

            CustomResponse<?> responseBody = new CustomResponse<>(updateUser, "UPDATED", HttpStatus.OK.value(),
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

    @Override
    public ResponseEntity<?> deleteUser(HttpServletRequest req, HttpServletResponse res, String id) {

        try {

            if (id.isBlank()) {

                String errorMessages = "Id is required!";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            Optional<UserEntity> deleteUser = adminDAO.deleteUserInfo(id);

            if (deleteUser.isEmpty()) {

                String errorMessage = "User not found with ID: " + id;

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessage, "NOT_FOUND",
                        HttpStatus.NOT_FOUND.value(), req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
            }

            CustomResponse<?> responseBody = new CustomResponse<>("User deleted successfully", "DELETED",
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

    @Override
    public ResponseEntity<?> uploadUsersData(HttpServletRequest req,
            HttpServletResponse res, MultipartFile file, UserInfoDTO userDetails) {

        try {
            if (file.isEmpty()) {

                String errorMessage = "File is Empty !";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessage,
                        "NOT_FOUND",
                        HttpStatus.NOT_FOUND.value(), req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);

            }

            if (!file.getOriginalFilename().endsWith(".xls")
                    && !file.getOriginalFilename().endsWith(".xlsx")) {
                String errorMessage = "Invalid file type! Please upload a valid Excel file.";
                CustomResponse<String> responseBody = new CustomResponse<>(errorMessage,
                        "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            List<UserEntity> users = new ArrayList<>();
            List<String> emails = new ArrayList<>();

            try {
                Workbook workbook = new XSSFWorkbook(file.getInputStream());
                Sheet sheet = workbook.getSheetAt(0);
                Row headerRow = sheet.getRow(0);

                validateUserHeaders(headerRow);

                System.out.println(sheet.getLastRowNum());

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    // Skip rows where any cell has an empty string
                    if (isRowEmpty(row)) {
                        continue;
                    }
                    UserEntity user = validateParseRow(row, userDetails);
                    users.add(user);
                }

                for (int emailIndex = 1; emailIndex <= sheet.getLastRowNum(); emailIndex++) {
                    Row row = sheet.getRow(emailIndex);
                    emails.add(row.getCell(1).toString());
                }

                List<UserEntity> existingUsers = adminDAO.getExisitingUsers(emails);

                if (existingUsers.toArray().length > 0) {

                    List<String> emailList = existingUsers.stream()
                            .map(UserEntity::getEmail) // Extracts only the email field
                            .toList();

                    String emailString = String.join(", ", emailList);

                    String message = "User email already exists: " + emailString;

                    CustomResponse<String> responseBody = new CustomResponse<>(message,
                            "NOT_FOUND",
                            HttpStatus.NOT_FOUND.value(), req.getRequestURI(), LocalDateTime.now());

                    return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
                }

            } catch (Exception e) {

                CustomResponse<String> responseBody = new CustomResponse<>(e.getMessage(),
                        "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);

            }

            List<UserEntity> userList = adminDAO.uploadUserInfo(users);

            CustomResponse<?> responseBody = new CustomResponse<>(userList, "SUCCESS",
                    HttpStatus.OK.value(),
                    req.getRequestURI(), LocalDateTime.now());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        } catch (Exception e) {
            CustomResponse<String> responseBody = new CustomResponse<>(e.getMessage(),
                    "BAD_REQUEST",
                    HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    private void validateUserHeaders(Row headerRow) {
        List<String> requiredHeaders = List.of("username", "email", "phone",
                "country", "state", "dob");
        for (int i = 0; i < requiredHeaders.size(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell == null
                    || !requiredHeaders.get(i).equalsIgnoreCase(cell.getStringCellValue().trim())) {
                throw new IllegalArgumentException("Invalid column name: "
                        + requiredHeaders.get(i));
            }
        }
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (Cell cell : row) {
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String cellValue = cell.toString().trim();
                if (!cellValue.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private UserEntity validateParseRow(Row row, UserInfoDTO userDetails) {
        UserEntity user = new UserEntity();

        // Assuming the expected columns are in specific positions (adjust as needed)
        // For example, column 0 is "username", column 1 is "email", etc.
        user.setUuid(utills.generateString(36));
        // Read "username" (column 0)
        Cell usernameCell = row.getCell(0);
        if (usernameCell != null && usernameCell.getCellType() == CellType.STRING) {
            user.setUserName(usernameCell.getStringCellValue().trim());
        } else {
            // Handle error if the value is not a string or is empty
            throw new IllegalArgumentException("Please enter the field username at "
                    + (row.getRowNum() + 1));
        }

        // Read "email" (column 1)
        Cell emailCell = row.getCell(1);
        if (emailCell != null && emailCell.getCellType() == CellType.STRING) {
            user.setEmail(emailCell.getStringCellValue().trim());
        } else {
            throw new IllegalArgumentException("Please enter the field email at "
                    + (row.getRowNum() + 1));
        }

        // Read "phone" (column 2)
        Cell phoneCell = row.getCell(2);
        if (phoneCell != null) {
            if (phoneCell.getCellType() == CellType.NUMERIC) {
                // Convert numeric phone number to string
                String phone = String.valueOf((long) phoneCell.getNumericCellValue());

                // Validate phone number format
                if (phone.matches("\\d{10}")) {
                    user.setPhone(phone);
                } else {
                    throw new IllegalArgumentException(
                            "Invalid phone number. Please enter a valid 10-digit phone number at"
                            + (row.getRowNum() + 1));
                }
            } else {
                throw new IllegalArgumentException(
                        "Invalid phone cell type. Phone number must be a string or numeric at"
                        + (row.getRowNum() + 1));
            }
        } else {
            throw new IllegalArgumentException(
                    "Phone field is missing. Please enter the phone number at "
                    + (row.getRowNum() + 1));
        }

        // Read "country" (column 3)
        Cell countryCell = row.getCell(3);
        if (countryCell != null && countryCell.getCellType() == CellType.STRING) {
            user.setCountry(countryCell.getStringCellValue().trim());
        } else {
            throw new IllegalArgumentException("Please enter the field country at"
                    + (row.getRowNum() + 1));
        }

        // Read "state" (column 4)
        Cell stateCell = row.getCell(4);
        if (stateCell != null && stateCell.getCellType() == CellType.STRING) {
            user.setState(stateCell.getStringCellValue().trim());
        } else {
            throw new IllegalArgumentException("Please enter the field state at"
                    + (row.getRowNum() + 1));
        }
        // Read "dob" (column 5), assuming it's a date (you can adjust for other
        // formats)
        Cell dobCell = row.getCell(5);
        if (dobCell != null) {
            if (dobCell.getCellType() == CellType.NUMERIC) {
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(dobCell)) {
                    // Convert date to string in desired format
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    user.setDob(sdf.format(dobCell.getDateCellValue())); // Convert date to string
                } else {
                    throw new IllegalArgumentException("Please enter the Valid dob at"
                            + (row.getRowNum() + 1));
                }
            } else {
                throw new IllegalArgumentException("DOB number should be in "
                        + dobCell.getCellType() + (row.getRowNum() + 1));
            }
        } else {
            throw new IllegalArgumentException("Please enter the field dob at "
                    + (row.getRowNum() + 1));
        }

        user.setRole("ROLE_STUDENT,");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy(userDetails.getUuid());
        SecureRandom secureRandom = new SecureRandom();
        String sixDigitNumber = String.valueOf(100000 + secureRandom.nextInt(900000)); // Generates a number between 100000 and 999999
        System.out.println("6-Digit Secure Random Number: " + sixDigitNumber);
        user.setRollNo(sixDigitNumber);

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(utills.generateRandomPassword()); // Set a random password if it's not provided
        }
        System.out.println(user.getUserName());
        return user;
    }

    @Override
    public ResponseEntity<?> fetchUserBooksByUserId(HttpServletRequest req, HttpServletResponse res, UserInfoDTO userDetails) {
        // TODO Auto-generated method stub

        try {

            if (userDetails.getId().isEmpty()) {

                String errorMessages = "Id is required!";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            List<UserBookViewDTO> getUserBooks = adminDAO.getUserBooksById(userDetails.getId());

            CustomResponse<?> responseBody = new CustomResponse<>(getUserBooks, "SUCCESS",
                    HttpStatus.OK.value(),
                    req.getRequestURI(), LocalDateTime.now());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        } catch (Exception e) {

            CustomResponse<String> responseBody = new CustomResponse<>(e.getMessage(),
                    "BAD_REQUEST",
                    HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);

        }
    }

}
