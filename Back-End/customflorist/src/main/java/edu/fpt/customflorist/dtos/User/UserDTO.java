package edu.fpt.customflorist.dtos.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.fpt.customflorist.models.Enums.Gender;
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
    @NotBlank(message = "gender is required")
    private Gender gender;

}
