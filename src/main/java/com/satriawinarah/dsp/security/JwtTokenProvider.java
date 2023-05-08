package com.satriawinarah.dsp.security;

import com.satriawinarah.dsp.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenProvider {

    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;
    private final long expirationTimeInMillis;

    public JwtTokenProvider(JwtBuilder jwtBuilder, JwtParser jwtParser, long expirationTimeInMillis) {
        this.jwtBuilder = jwtBuilder;
        this.jwtParser = jwtParser;
        this.expirationTimeInMillis = expirationTimeInMillis;
    }

    public String createToken(String phoneNumber) {
        Date expiryTime = new Date(System.currentTimeMillis() + expirationTimeInMillis);
        Date issuedTime = new Date();

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", phoneNumber);
        claims.put("iat", issuedTime);
        claims.put("exp", expiryTime);

        return jwtBuilder
                .setClaims(claims)
                .setIssuedAt(issuedTime)
                .setExpiration(expiryTime)
                .compact();
    }

    public String validateToken(String bearerToken) throws InvalidTokenException {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            Claims claims = jwtParser.parseClaimsJws(token).getBody();

            if (new Date().before(claims.getExpiration())) {
                return claims.get("sub", String.class);
            } else {
                throw new InvalidTokenException();
            }
        }

        throw new InvalidTokenException();
    }

}
