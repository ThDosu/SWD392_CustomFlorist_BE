package edu.fpt.customflorist.services.Token;

import edu.fpt.customflorist.models.Token;
import edu.fpt.customflorist.models.User;

public interface ITokenService {
    Token addToken(User user, String token, boolean isMobileDevice);
    Token refreshToken(String refreshToken, User user) throws Exception;
}
