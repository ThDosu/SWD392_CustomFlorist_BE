package edu.fpt.customflorist.services.User;

import edu.fpt.customflorist.dtos.UpdateUserDTO;
import edu.fpt.customflorist.dtos.UserDTO;
import edu.fpt.customflorist.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String userName, String password) throws Exception;
    void updateAccount(String token, UpdateUserDTO updateUserDTO) throws Exception;
    User getUserDetails(String token) throws Exception;
    User getUserById(Long userID) throws Exception;
}
