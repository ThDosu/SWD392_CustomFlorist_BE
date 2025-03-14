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
import java.util.Map;

@RequiredArgsConstructor
public class GoogleOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final WebClient userInfoClient;

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        if (!isGoogleToken(token)) {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("sub", "anonymous");
            return new OAuth2IntrospectionAuthenticatedPrincipal("anonymous", attributes, null);
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
