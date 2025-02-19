package edu.fpt.customflorist.responses.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.fpt.customflorist.models.Enums.AccountStatus;
import edu.fpt.customflorist.models.Enums.Role;
import edu.fpt.customflorist.models.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("loyaltyPoints")
    private Integer loyaltyPoints;

    @JsonProperty("accountStatus")
    private AccountStatus accountStatus;

    @JsonProperty("role")
    private Role role;

    public static CustomerResponse fromUser(User user) {
        return CustomerResponse.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .name(user.getName())
                .address(user.getAddress())
                .email(user.getEmail())
                .phone(user.getPhone())
                .loyaltyPoints(user.getLoyaltyPoints())
                .accountStatus(user.getAccountStatus())
                .role(user.getRole())
                .build();
    }
}
