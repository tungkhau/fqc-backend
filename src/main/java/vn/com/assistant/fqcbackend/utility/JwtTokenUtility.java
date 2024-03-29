package vn.com.assistant.fqcbackend.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.entity.Token;
import vn.com.assistant.fqcbackend.entity.User;
import vn.com.assistant.fqcbackend.service.TokenService;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtTokenUtility implements Serializable {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    private final TokenService tokenService;

    private Key getKey() {
        byte[] keyByte = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public String getUsernameFromJwt(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String generateToken(Authentication authentication) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expiration * 60 * 1000 * 24 * 60);
        User userDetails = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(getKey())
                .compact();
    }

    public boolean validateToken(String token)  throws Exception {
            Jwts.parserBuilder().setSigningKey(getKey()).build()
                    .parseClaimsJws(token);
            checkExistedToken(token);
            return true;
    }

    private void checkExistedToken(String token) throws Exception {
        boolean isValidToken = false;
        List<Token> tokenList= tokenService.fetch();
        for (Token tkn: tokenList) {
            if (tkn.getValue().equals(token)) {
                isValidToken = true;
                break;
            }
        }
        if (!isValidToken) throw new Exception("Token khong ton tai");
    }

}