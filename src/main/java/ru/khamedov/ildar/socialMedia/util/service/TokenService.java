package ru.khamedov.ildar.socialMedia.util.service;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.khamedov.ildar.socialMedia.model.UserProfile;
import ru.khamedov.ildar.socialMedia.util.Constant;

public class TokenService {

    @Value("${spring.security.jwt.secret.algorithm}")
    private String algorithm;

    @Resource
    private JwtEncoder jwtEncoder;

    @Resource
    private JwtDecoder jwtDecoder;

    public String createToken(UserProfile userProfile){
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .claim(Constant.SCOPE,Constant.SCOPE_VALUE)
                .claim(Constant.USER_NAME,userProfile.getName())
                .build();
        JwsHeader jwsHeader = JwsHeader.with(() -> algorithm).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader,claims)).getTokenValue();
    }
}
