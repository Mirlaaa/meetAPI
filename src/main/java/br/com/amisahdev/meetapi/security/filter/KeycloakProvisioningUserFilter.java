package br.com.amisahdev.meetapi.security.filter;

import br.com.amisahdev.meetapi.model.UserEntity;
import br.com.amisahdev.meetapi.security.AuthenticatedUser;
import br.com.amisahdev.meetapi.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class KeycloakProvisioningUserFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {

        final Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof JwtAuthenticationToken jwtAuth
                && auth.getPrincipal() instanceof Jwt jwt) {

            final UserEntity userCreated =
                    userService.getOrCreate(jwt);

            final AuthenticatedUser user = new AuthenticatedUser(
                    userCreated.getId(),
                    userCreated.getKeycloakUserId(),
                    userCreated.getEmail()
            );

            final Authentication newAuth =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            jwt,
                            auth.getAuthorities()
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(newAuth);
        }

        filterChain.doFilter(request, response);
    }

}
