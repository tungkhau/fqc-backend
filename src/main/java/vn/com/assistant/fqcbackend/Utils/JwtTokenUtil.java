package vn.com.assistant.fqcbackend.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.entity.Token;
import vn.com.assistant.fqcbackend.entity.UserCredential;
import vn.com.assistant.fqcbackend.service.TokenService;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtTokenUtil implements Serializable {
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

    public String generateToken(Authentication authen) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expiration * 60 * 1000 * 24 * 60);
        UserCredential userDetails = (UserCredential) authen.getPrincipal();
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
            }
        }
        if (!isValidToken) throw new Exception("Token khong ton tai");
    }

    public Claims getClaims(final String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }
}