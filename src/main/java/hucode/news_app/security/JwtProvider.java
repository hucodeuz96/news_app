package hucode.news_app.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtProvider {
    @Value("${jwt.secretkey}")
    private String key;
    @Value("${jwt.token.ttl}")
    private Long ttl ;
    public String generateToken(String  username){
       return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ttl))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
    public String getEmailFromToken(String token){
       return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
