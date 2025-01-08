package com.library_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library_management.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {

    List<BookEntity> findByBookNameIn(List<String> bookNames);

}
