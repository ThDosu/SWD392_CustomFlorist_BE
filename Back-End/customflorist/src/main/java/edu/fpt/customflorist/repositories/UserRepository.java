package edu.fpt.customflorist.repositories;

import edu.fpt.customflorist.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername (String username);
    Optional<User> findByUsername(String username);
    boolean existsByPhone(String phone);
    Optional<User> findByUserId(Long id);
}
