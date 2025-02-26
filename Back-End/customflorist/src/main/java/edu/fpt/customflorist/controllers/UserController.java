package edu.fpt.customflorist.controllers;

import edu.fpt.customflorist.dtos.User.UpdateUserDTO;
import edu.fpt.customflorist.dtos.User.UserDTO;
import edu.fpt.customflorist.dtos.User.UserLoginDTO;
import edu.fpt.customflorist.models.User;
import edu.fpt.customflorist.responses.ResponseObject;
import edu.fpt.customflorist.responses.User.CustomerResponse;
import edu.fpt.customflorist.services.User.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/api/v1/users")
public class UserController {
    private final IUserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ) {
        try{
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            User user = userService.createUser(userDTO);

            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .message("Create account successfully")
                            .data(CustomerResponse.fromUser(user))
                            .status(HttpStatus.OK)
                            .build()
            );
        }  catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO,
            BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            String token = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());

            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .message("Login successfully")
                            .data(token)
                            .status(HttpStatus.OK)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam(required = false) String keyword,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "50") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<CustomerResponse> users = userService.findAll(keyword, pageable).map(CustomerResponse::fromUser);

            return ResponseEntity.ok(ResponseObject.builder()
                    .message("Fetch users successfully")
                    .data(users)
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(ResponseObject.builder()
                    .message("Fetch user successfully")
                    .data(CustomerResponse.fromUser(user))
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        try {
            User updatedUser = userService.updateUser(id, updateUserDTO);
            return ResponseEntity.ok(ResponseObject.builder()
                    .message("User updated successfully")
                    .data(CustomerResponse.fromUser(updatedUser))
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> blockOrEnableUser(@PathVariable Long id, @RequestParam Boolean active) {
        try {
            userService.blockOrEnable(id, active);
            return ResponseEntity.ok(ResponseObject.builder()
                    .message(active ? "User enabled successfully" : "User blocked successfully")
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> resetPassword(@PathVariable Long id, @RequestParam String oldPassword, @RequestParam String newPassword) {
        try {
            userService.resetPassword(id, oldPassword, newPassword);
            return ResponseEntity.ok(ResponseObject.builder()
                    .message("Password reset successfully")
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
