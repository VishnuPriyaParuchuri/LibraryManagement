package com.library_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library_management.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {

    List<BookEntity> findByBookNameIn(List<String> bookNames);

    @Query(value = """
                     SELECT * FROM fetch_book_with_users 
        WHERE 
            CASE 
                WHEN :searchKey IS NULL THEN 1
                WHEN :searchKey IS NOT NULL AND LOWER(TRIM(author)) LIKE '%' || LOWER(:searchKey) || '%' 
                OR :searchKey IS NOT NULL AND LOWER(TRIM(book_name)) LIKE '%' || LOWER(:searchKey) || '%'
              then 1  
            END = 1
    """, nativeQuery = true)
    List<Object[]> findBooksWithUserDetails(@Param("searchKey") String searchKey);

}
