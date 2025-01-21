package com.library_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library_management.entity.StudentBookEntity;

@Repository
public interface StudentBookRepo extends JpaRepository<StudentBookEntity, String> {

    Optional<StudentBookEntity> findByBookIdAndUserId(String bookId, String userId);

}
