package org.example.sourdough.service.users;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver);
    public String extractUsername(String jwt);
    String generateToken(UserDetails userDetails);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    boolean isValidJwt(String jwt, UserDetails userDetails);
}
