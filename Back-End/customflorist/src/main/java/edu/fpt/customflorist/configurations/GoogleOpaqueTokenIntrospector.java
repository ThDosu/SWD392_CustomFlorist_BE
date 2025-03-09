package edu.fpt.customflorist.configurations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.fpt.customflorist.dtos.User.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GoogleOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final WebClient userInfoClient;

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        if (!isGoogleToken(token)) {
            DecodedJWT decodedJWT = JWT.decode(token);

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("sub", decodedJWT.getSubject());
            attributes.put("name", decodedJWT.getClaim("FullName").asString());
            attributes.put("email", decodedJWT.getClaim("Email").asString());

            List<String> roles = decodedJWT.getClaim("Role").asList(String.class);
            if (roles != null) {

                List<String> authorities = roles.stream()
                        .map(role -> "ROLE_" + role)
                        .collect(Collectors.toList());
                attributes.put("Role", authorities);
            }

            return new OAuth2IntrospectionAuthenticatedPrincipal(decodedJWT.getClaim("FullName").asString(), attributes, null);
        }
        UserInfo userInfo = userInfoClient.get()
                .uri( uriBuilder -> uriBuilder
                        .path("/oauth2/v3/userinfo")
                        .queryParam("access_token", token)
                        .build())
                .retrieve()
                .bodyToMono(UserInfo.class)
                .block();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", userInfo.sub());
        attributes.put("name", userInfo.name());
        attributes.put("email", userInfo.email());
        return new OAuth2IntrospectionAuthenticatedPrincipal(userInfo.name(), attributes, null);
    }

    private boolean isGoogleToken(String token) {
        return token != null && token.startsWith("ya29.");
    }

}
