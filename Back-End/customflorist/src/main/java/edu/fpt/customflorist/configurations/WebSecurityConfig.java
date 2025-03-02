package edu.fpt.customflorist.configurations;

import edu.fpt.customflorist.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig implements WebMvcConfigurer {

    private final WebClient userInfoClient;
    private final JwtTokenFilter jwtTokenFilter;
    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**",
                                    "/swagger-resources/**",
                                    "/swagger-ui.html",
                                    "/webjars/**"
                            )
                            .permitAll()

                            .requestMatchers(HttpMethod.GET, String.format("%s/api/v1/auth/**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("/auth/**")).permitAll()

                            .requestMatchers(HttpMethod.POST, String.format("%s/api/v1/users/signup", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.POST, String.format("%s/api/v1/users/login", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.POST, String.format("%s/api/v1/users/reset-password/**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("%s/api/v1/users", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("%s/api/v1/users/**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.PUT, String.format("%s/api/v1/users/**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.PATCH, String.format("%s/api/v1/users/**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("%s/api/v1/users/verify", apiPrefix)).permitAll()

                            .requestMatchers(HttpMethod.GET, String.format("%s/api/v1/payment/vn-pay-callback", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.POST, String.format("%s/api/v1/payment/vn-pay", apiPrefix)).permitAll()

                            .requestMatchers("/payment-fail.html", "/payment-success.html","/error.html")
                            .permitAll()

                            .requestMatchers(HttpMethod.GET, String.format("%s/api/v1/categories", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("%s/api/v1/categories/**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.POST, String.format("%s/api/v1/categories", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.PUT, String.format("%s/api/v1/categories/**", apiPrefix)).permitAll()

                            .requestMatchers(HttpMethod.GET, String.format("%s/api/v1/feedbacks", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("%s/api/v1/feedbacks/**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.POST, String.format("%s/api/v1/feedbacks", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.PUT, String.format("%s/api/v1/feedbacks/**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/api/v1/feedbacks/**", apiPrefix)).permitAll()

                            .requestMatchers(HttpMethod.GET, String.format("%s/api/v1/delivery-histories", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("%s/api/v1/delivery-histories/**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.POST, String.format("%s/api/v1/delivery-histories", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.PUT, String.format("%s/api/v1/delivery-histories/**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/api/v1/delivery-histories/**", apiPrefix)).permitAll()

                            .anyRequest().authenticated();

                })
                .oauth2ResourceServer(c -> c.opaqueToken(Customizer.withDefaults()))
                .csrf(AbstractHttpConfigurer::disable);

        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*", "http://localhost:4300", "http://localhost:4200"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList(
                        "authorization", "content-type", "x-auth-token", "accept",
                        "access-control-request-method", "access-control-request-headers"
                ));
                configuration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });

        return http.build();
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // Allow all paths
//                .allowedOrigins("http://localhost:3000") // Allow only this origin
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD") // Allow these methods
//                .allowedHeaders("*") // Allow all headers
//                .allowCredentials(true); // Allow credentials (cookies, authorization headers, etc.)
//    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    @Bean
    public OpaqueTokenIntrospector introspector() {
        return new GoogleOpaqueTokenIntrospector(userInfoClient);
    }
}
