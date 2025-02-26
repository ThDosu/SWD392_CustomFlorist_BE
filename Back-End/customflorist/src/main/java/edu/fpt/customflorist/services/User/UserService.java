package edu.fpt.customflorist.services.User;

import edu.fpt.customflorist.components.JwtTokenUtils;
import edu.fpt.customflorist.dtos.User.UpdateUserDTO;
import edu.fpt.customflorist.dtos.User.UserDTO;
import edu.fpt.customflorist.exceptions.DataNotFoundException;
import edu.fpt.customflorist.exceptions.InvalidPasswordException;
import edu.fpt.customflorist.exceptions.UserException;
import edu.fpt.customflorist.models.Enums.AccountStatus;
import edu.fpt.customflorist.models.Enums.Gender;
import edu.fpt.customflorist.models.Enums.Role;
import edu.fpt.customflorist.models.User;
import edu.fpt.customflorist.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final Role roleDefault = Role.CUSTOMER;
    private final AccountStatus accountStatusDefault = AccountStatus.ACTIVE;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        if(userRepository.existsByPhone(userDTO.getPhone())) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        //convert from userDTO => user
        User newUser = User.builder()
                .password(userDTO.getPassword())
                .name(userDTO.getName())
                .address(userDTO.getAddress())
                .phone(userDTO.getPhone())
                .email(userDTO.getEmail())
                .loyaltyPoints(0)
                .gender(Gender.valueOf(userDTO.getGender()))
                .accountStatus(accountStatusDefault)
                .role(roleDefault)
                .build();

        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
        return userRepository.save(newUser);
    }

    @Override
    public String login(String email, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new DataNotFoundException("Invalid email / password");
        }

        User existingUser = optionalUser.get();
        if(existingUser.getAccountStatus() == AccountStatus.BANNED){
            throw new UserException("User is not active");
        }

        if(!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new BadCredentialsException("Wrong email or password");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email, password,
                existingUser.getAuthorities()
        );

        //authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public String generateTokenForGoogleAccount(OAuth2AuthenticatedPrincipal principal) throws Exception {

        String name = (String) principal.getAttributes().get("name");
        String email = (String) principal.getAttributes().get("email");

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User existingUser;

        if (optionalUser.isPresent()) {
            existingUser = optionalUser.get();
        } else {
            existingUser = new User();
            existingUser.setEmail(email);
            existingUser.setName(name);
            existingUser.setAccountStatus(AccountStatus.ACTIVE);
            existingUser.setPassword("");
            existingUser.setRole(Role.CUSTOMER);

            userRepository.save(existingUser);
        }

        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        String email = jwtTokenUtil.extractUserName(token);
        return userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("User not found"));
    }

    @Override
    public Page<User> findAll(String keyword, Pageable pageable) throws Exception {
        return userRepository.searchUsers(keyword == null || keyword.isBlank() ? null : keyword, pageable);
    }

    @Override
    public void resetPassword(Long userId, String oldPassword, String newPassword)
            throws InvalidPasswordException, DataNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidPasswordException("Old password is incorrect");
        }

        if (newPassword.length() < 6) {
            throw new InvalidPasswordException("Password must be at least 6 characters long");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void blockOrEnable(Long userId, Boolean active) throws DataNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
        user.setAccountStatus(active ? AccountStatus.ACTIVE : AccountStatus.BANNED);
        userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));

        if (updatedUserDTO.getName() != null) {
            user.setName(updatedUserDTO.getName());
        }
        if (updatedUserDTO.getAddress() != null) {
            user.setAddress(updatedUserDTO.getAddress());
        }
        if (updatedUserDTO.getPhone() != null) {
            if (userRepository.existsByPhone(updatedUserDTO.getPhone()) && !user.getPhone().equals(updatedUserDTO.getPhone())) {
                throw new DataIntegrityViolationException("Phone number already exists");
            }
            user.setPhone(updatedUserDTO.getPhone());
        }
        if (updatedUserDTO.getGender() != null) {
            user.setGender(Gender.valueOf(updatedUserDTO.getGender()));
        }

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) throws DataNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
    }

}
