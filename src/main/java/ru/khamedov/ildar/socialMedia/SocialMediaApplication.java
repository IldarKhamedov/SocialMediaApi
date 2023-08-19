package ru.khamedov.ildar.socialMedia;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.khamedov.ildar.socialMedia.security.UserProfileDetailService;
import ru.khamedov.ildar.socialMedia.util.service.TokenService;
import ru.khamedov.ildar.socialMedia.util.service.UserProfileService;
import ru.khamedov.ildar.socialMedia.util.Constant;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@SpringBootApplication
public class SocialMediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialMediaApplication.class, args);
    }

    @Value("${spring.security.jwt.secret.key}")
    private String secret_key;

    @Value("${spring.security.jwt.secret.algorithm}")
    private String algorithm;

    @Bean
    public UserProfileService userProfileService() {
        return new UserProfileService();
    }

    @Bean
    public TokenService tokenService() {
        return new TokenService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user/register").permitAll()
                        .requestMatchers("/api/user/token").permitAll()
                        .requestMatchers("/api/social/**").hasAnyAuthority(Constant.AUTHORITY_ROLE, Constant.AUTHORITY_SCOPE)
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .authenticationProvider(daoAuthenticationProvider())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userProfileDetailService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public UserProfileDetailService userProfileDetailService() {
        return new UserProfileDetailService();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey secretKey = new SecretKeySpec(secret_key.getBytes(), algorithm);
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        SecretKey key = new SecretKeySpec(secret_key.getBytes(), algorithm);
        return new NimbusJwtEncoder(new ImmutableSecret(key));
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
