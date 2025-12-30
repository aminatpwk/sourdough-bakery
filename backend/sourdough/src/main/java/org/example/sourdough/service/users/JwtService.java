package org.example.sourdough.service.users;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.function.Function;

public interface JwtService {
    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver);
    public String extractUsername(String jwt);
}
