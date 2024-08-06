package com.example.CartService.utils;

import com.example.CartService.model.JwtObject;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
@Component
public class JwtUtil {

//    public Map<String , Object> getClaims(@AuthenticationPrincipal JwtObject jwt){
//        return jwt.getClaims();
//    }
private final JwtDecoder jwtDecoder;

    public JwtUtil(@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuerUri) {
        this.jwtDecoder = JwtDecoders.fromIssuerLocation(issuerUri);
    }

    public JwtObject convertTokenToJwtObject(String token) {
        try {
            // Parse the JWT token
            Jwt jwt = jwtDecoder.decode(token.split(" ")[1]);

            // Convert claims to JwtObject
            JwtObject jwtObject = new JwtObject();
            jwtObject.setCreatedAt( jwt.getClaimAsInstant("iat").toEpochMilli());
            jwtObject.setExpiryAt(jwt.getClaimAsInstant("exp").toEpochMilli());
            jwtObject.setRoles(jwt.getClaim("roles"));
            jwtObject.setUserId(jwt.getClaim("userId"));

            return jwtObject;
        } catch (Exception e) {
            throw new RuntimeException("Error while extracting JWT claims", e);
        }
    }


}
