package pda.server.Auth;

import org.springframework.beans.factory.annotation.Value;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenUtil {
    public static final long JWT_EX_TIME =  60 * 30;

    //@Value("${spring.jwt.secret}")
    private String SecretKey = "ENC(/Bvd6nnUzO/iiNfE7JzUm2+A7mPWj+FFn0cDWdk/2sk=)";

    public String getUIDFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SecretKey).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String doGenerateToken(String subject) {
        return Jwts.builder().setClaims(new HashMap<>()).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EX_TIME * 1000))
                .signWith(SignatureAlgorithm.HS512, SecretKey).compact();
    }
}
