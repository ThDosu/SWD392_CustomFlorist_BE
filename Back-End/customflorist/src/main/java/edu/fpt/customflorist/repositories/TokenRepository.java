package edu.fpt.customflorist.repositories;

import edu.fpt.customflorist.models.Token;
import edu.fpt.customflorist.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUser(User user);
    Token findByToken(String token);
    Token findByRefreshToken(String token);
}
