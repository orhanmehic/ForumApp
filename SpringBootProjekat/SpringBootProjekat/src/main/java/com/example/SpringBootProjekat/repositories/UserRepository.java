package com.example.SpringBootProjekat.repositories;

import com.example.SpringBootProjekat.entities.UserEntity;
import com.example.SpringBootProjekat.services.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
     boolean existsByUsername(String username);

    @Query(value = "SELECT u FROM UserEntity u WHERE u.role = 'user'")
    List<UserEntity> findAllUsers();
}
