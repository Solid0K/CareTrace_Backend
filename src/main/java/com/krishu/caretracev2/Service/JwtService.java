package com.krishu.caretracev2.Service;

import com.krishu.caretracev2.ClientRole;
import com.krishu.caretracev2.Model.Client;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Client client){
        return Jwts.builder().subject(client.getId()).claim("Email",client.getEmail()).claim("Role",client.getRole()).
                issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+ 1000L * 60 * 60 * 24)).signWith(getKey()).compact();
    }

    public String extractId(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token, String email){
        String extractedEmail = extractId(token);
        return extractedEmail.equals(email) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        Date expiration = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expiration.before(new Date());
    }
}
