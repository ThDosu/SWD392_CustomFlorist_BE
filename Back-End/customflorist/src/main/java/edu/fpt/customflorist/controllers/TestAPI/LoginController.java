package edu.fpt.customflorist.controllers.TestAPI;

import edu.fpt.customflorist.responses.ResponseObject;
import edu.fpt.customflorist.services.User.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {
    private final IUserService userService;

    @GetMapping("/google-login")
    public void googleLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }

    @GetMapping("/loginSuccess")
    public ResponseEntity<?> loginSuccess(OAuth2AuthenticationToken authentication) {
        try {
            OAuth2User user = authentication.getPrincipal();

            String token = userService.loginGoogle(user);

            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Login successfully")
                            .data(token)
                            .status(HttpStatus.OK)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ResponseObject.builder()
                            .message("Login failed: " + e.getMessage())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .build()
            );
        }
    }

    @PostMapping("/exchange-token")
    public ResponseEntity<?> exchangeToken(@RequestBody Map<String, String> requestBody) {
        String authCode = requestBody.get("code");
        if (authCode == null || authCode.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .message("Authorization code is missing")
                    .status(HttpStatus.BAD_REQUEST)
                    .build());
        }

        try {
            String token = userService.exchangeAuthCodeForToken(authCode);
            return ResponseEntity.ok(ResponseObject.builder()
                    .message("Login successfully")
                    .data(token)
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ResponseObject.builder()
                            .message("Login failed: " + e.getMessage())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .build()
            );
        }
    }

    @GetMapping("/loginFailure")
    public ResponseEntity<?> loginFailure(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String errorMessage = "Login failed. Please try again!";

        if (session != null) {
            Object errorObj = session.getAttribute("errorMessage");
            if (errorObj != null) {
                errorMessage = errorObj.toString();
                session.removeAttribute("errorMessage");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ResponseObject.builder()
                        .message(errorMessage)
                        .status(HttpStatus.UNAUTHORIZED)
                        .build()
        );
    }

}

