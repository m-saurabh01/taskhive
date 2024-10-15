package com.sync.workflow.taskhiveusermanagement.repository;

import com.sync.workflow.taskhiveusermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
