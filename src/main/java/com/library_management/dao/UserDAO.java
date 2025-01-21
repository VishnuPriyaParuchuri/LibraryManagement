package com.library_management.dao;

import java.util.List;

public interface UserDAO {

    List<Object[]> getAllBooks(String searchKey);

}
