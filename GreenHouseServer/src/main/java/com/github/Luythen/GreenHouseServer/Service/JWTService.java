package com.github.Luythen.GreenHouseServer.Service;

import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.Luythen.GreenHouseServer.Exceptions.InvalidFieldException;
import com.github.Luythen.GreenHouseServer.Model.UserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    @Value("${access_token_secret_key}")
    private String ACCESS_TOKEN_SECRET_KEY;

    @Value("${refresh_token_secret_key}")
    private String REFRESH_TOKEN_SECRET_KEY;

    @Value("${jwt_token_issuer}")
    private String Issuer;

    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1200000; // 20 min
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 432000000; // 5 days

    private static final long API_KEY_TOKEN_EXPIRATION_TIME = Duration.ofDays(365).toMillis(); // 1 year

    public String generateRefreshToken (UserModel userModel) {
        return Jwts.builder()
            .subject(userModel.getEmail())
            .issuer(Issuer)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
            .signWith(Keys.hmacShaKeyFor(REFRESH_TOKEN_SECRET_KEY.getBytes()))
            .compact();
    }

    public String generateAccessToken (UserModel userModel) {
        return Jwts.builder()
            .subject(userModel.getEmail())
            .issuer(Issuer)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
            .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET_KEY.getBytes()))
            .compact();
    }

    public String generateApiKey (String greenHouseId, String userId) {
         return Jwts.builder()
            .claim("GreenHouseId", greenHouseId)
            .claim("OwnerId", userId)
            .issuer(Issuer)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + API_KEY_TOKEN_EXPIRATION_TIME))
            .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET_KEY.getBytes()))
            .compact();
    }

    public Claims getClaims (String token, String keyType) {
        try {
            String key = keyType == "accessToken" ? ACCESS_TOKEN_SECRET_KEY : REFRESH_TOKEN_SECRET_KEY;
            return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(key.getBytes())).build().parseSignedClaims(token).getPayload();
        } catch (MalformedJwtException e) {
            throw new InvalidFieldException("token är fel");
        }
    }

    public boolean isTokenExpired (String token, String keyType) {
        return getClaims(token, keyType).getExpiration().before(new Date());
    }

}
