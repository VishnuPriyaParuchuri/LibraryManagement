package com.library_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.library_management.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {

    List<BookEntity> findByBookNameIn(List<String> bookNames);

    @Query(value = """
                SELECT
                    b.id AS bookId,
                    b.book_name AS bookName,
                    b.author AS author,
                    b.description AS description,
                    JSON_ARRAYAGG(
                        JSON_OBJECT(
                            'userId', u.id,
                            'userName', u.user_name,
                            'phone', u.phone,
                            'email', u.email,
                            'rollNo', u.roll_no,
                            'status', sb.status
                        )
                    ) AS userList
                FROM
                    lm_book b
                LEFT JOIN
                    lm_student_book sb ON b.id = sb.book_id
                LEFT JOIN
                    lm_user u ON sb.user_id = u.id
                GROUP BY
                    b.id, b.book_name, b.author, b.description
                    
            """, nativeQuery = true)
    List<Object[]> findBookswithUserDetails();

}
