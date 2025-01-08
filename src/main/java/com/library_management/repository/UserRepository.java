package com.library_management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library_management.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    public Optional<UserEntity> findByEmail(String email);

    Page<UserEntity> findByRole(@Param("role") String role, Pageable pageable);

    @Query("SELECT u FROM UserEntity u WHERE u.email IN :emails")
    List<UserEntity> findUsersByEmails(@Param("emails") List<String> emails);

}
