package bc.bookchat.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtProvider {

    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    @Value("${jwt.secret}")
    private String secretKey;


    public String createAccessToken(String userFormId) {
        Claims claims = Jwts.claims()
                .setSubject(userFormId);

        Date now = new Date();
        Date accessTokenExpiresIn = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setIssuedAt(now)
                .setClaims(claims)
                .setExpiration(accessTokenExpiresIn)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getPayload(String token) {
        Jws<Claims> claim = parseClaims(token);
        return claim.getBody()
                .getSubject();
    }

    public Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
    }
}
