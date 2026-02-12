package br.com.amisahdev.meetapi.security.filter;

import br.com.amisahdev.meetapi.repository.UserRepository;
import br.com.amisahdev.meetapi.security.AuthenticatedUser;
import br.com.amisahdev.meetapi.security.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        final Claims claims = jwtService.extractClaims(token);

        final UUID userId = UUID.fromString(claims.getSubject());
        final String email = claims.get("username", String.class);

        final AuthenticatedUser authenticatedUser = new AuthenticatedUser(
                userId,
                email
        );

        final UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        authenticatedUser,
                        null,
                        List.of()
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);


        filterChain.doFilter(request, response);
    }
}

