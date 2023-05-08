package com.satriawinarah.dsp.config;

import com.satriawinarah.dsp.security.JwtTokenProvider;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

@Configuration
public class JwtConfig {

    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME_IN_MILLIS;

    @Value("${jwt.algorithm}")
    private String signatureAlgorithm;

    @Bean
    public KeyPair key() throws NoSuchAlgorithmException {
        return KeyPairGenerator.getInstance(SignatureAlgorithm.valueOf(signatureAlgorithm).getFamilyName())
                .generateKeyPair();
    }

    @Bean
    public JwtBuilder jwtBuilder() throws NoSuchAlgorithmException {
        return Jwts.builder().signWith(key().getPrivate());
    }

    @Bean
    public JwtParser jwtParser() throws NoSuchAlgorithmException {
        return Jwts.parserBuilder().setSigningKey(key().getPrivate()).build();
    }

    @Bean
    public JwtTokenProvider jwtTokenProvider() throws NoSuchAlgorithmException {
        return new JwtTokenProvider(jwtBuilder(), jwtParser(), EXPIRATION_TIME_IN_MILLIS);
    }

}
