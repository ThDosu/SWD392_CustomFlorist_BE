package edu.fpt.customflorist.services.User;

import edu.fpt.customflorist.components.JwtTokenUtil;
import edu.fpt.customflorist.dtos.UpdateUserDTO;
import edu.fpt.customflorist.dtos.UserDTO;
import edu.fpt.customflorist.exceptions.DataNotFoundException;
import edu.fpt.customflorist.exceptions.UserException;
import edu.fpt.customflorist.models.Enums.AccountStatus;
import edu.fpt.customflorist.models.Enums.Role;
import edu.fpt.customflorist.models.User;
import edu.fpt.customflorist.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final Role roleDefault = Role.CUSTOMER;
    private final AccountStatus accountStatusDefault = AccountStatus.ACTIVE;

    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        if(userRepository.existsByUsername(userDTO.getUsername())) {
            throw new DataIntegrityViolationException("Username already exists");
        }
        if(userRepository.existsByPhone(userDTO.getPhone())) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        //convert from userDTO => user
        User newUser = User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .name(userDTO.getName())
                .address(userDTO.getAddress())
                .phone(userDTO.getPhone())
                .email(userDTO.getEmail())
                .userCode("")
                .loyaltyPoints(0)
                .assignedOrders(0)
                .accountStatus(accountStatusDefault)
                .role(roleDefault)
                .build();

        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
        return userRepository.save(newUser);
    }

    @Override
    public String login(String username, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()) {
            throw new DataNotFoundException("Invalid username / password");
        }

        User existingUser = optionalUser.get();
        if(existingUser.getAccountStatus() == AccountStatus.INACTIVE){
            throw new UserException("User is not active");
        }

        if(!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new BadCredentialsException("Wrong username or password");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password,
                existingUser.getAuthorities()
        );

        //authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public void updateAccount(String token, UpdateUserDTO updateUserDTO) throws Exception {

    }

    @Override
    public User getUserDetails(String token) throws Exception {
        return null;
    }

    @Override
    public User getUserById(Long userID) throws Exception {
        return null;
    }
}
