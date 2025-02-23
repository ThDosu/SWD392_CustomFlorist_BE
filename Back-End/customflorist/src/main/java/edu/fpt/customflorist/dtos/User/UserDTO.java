package edu.fpt.customflorist.dtos.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    @JsonProperty("username")
    @NotBlank(message = "Username is mandatory")
    private String username;

    @JsonProperty("password")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @JsonProperty("name")
    @NotBlank(message = "Name is required")
    private String name;

    @JsonProperty("address")
    @NotBlank(message = "Address number is required")
    private String address;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    @NotBlank(message = "Phone is required")
    private String phone;

    @JsonProperty("gender")
    @NotBlank(message = "Gender is required")
    @Schema(description = "User's gender (Male/Female/Other)", example = "Choose one of 3 Male/Female/Other")
    private String gender;

}
