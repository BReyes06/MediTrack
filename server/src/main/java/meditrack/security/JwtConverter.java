package meditrack.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import meditrack.models.AppUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtConverter {

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final String ISSUER = "meditrack";
    private final int EXPIRATION_MINUTES = 15;
    private final int EXPIRATION_MILLISECONDS = EXPIRATION_MINUTES * 60 * 1000;

    public String getTokenFromUser(AppUser user) {
        String authorities = user.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setIssuer(ISSUER)
                .claim("username", user.getUsername())
                .claim("first_name", user.getFirstName())
                .claim("middle_name", user.getMiddleName())
                .claim("last_name", user.getLastName())
                .claim("email", user.getEmail())
                .claim("phone", user.getPhone())
                .claim("app_user_id", user.getAppUserId())
                .claim("authorities", authorities)
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_MILLISECONDS))
                .signWith(key)
                .compact();
    }

    public AppUser getUserFromToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .requireIssuer(ISSUER)
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.substring(7));

            int appUserId = (int) jws.getBody().get("app_user_id");
            String username = (String) jws.getBody().get("username");
            String firstName = (String) jws.getBody().get("first_name");
            String middleName = (String) jws.getBody().get("middle_name");
            String lastName = (String) jws.getBody().get("last_name");
            String email = (String) jws.getBody().get("email");
            String phone = (String) jws.getBody().get("phone");
            String authStr = (String) jws.getBody().get("authorities");

            List<SimpleGrantedAuthority> roles = Arrays.stream(authStr.split(","))
                    .map(r -> new SimpleGrantedAuthority(r))
                    .collect(Collectors.toList());

            return new AppUser(appUserId, firstName, middleName, lastName, email, phone, username, null, true, Arrays.asList(authStr.split(",")));
        } catch (JwtException ex) {
            // Exception acknowledged
        }

        return null;
    }
}
