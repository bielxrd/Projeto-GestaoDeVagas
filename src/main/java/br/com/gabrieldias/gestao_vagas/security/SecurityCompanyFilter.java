package br.com.gabrieldias.gestao_vagas.security;

import br.com.gabrieldias.gestao_vagas.providers.JWTProvider;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SecuriyCompanyFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//      SecurityContextHolder.getContext().setAuthentication(null);
        String authorization = request.getHeader("Authorization");

        if(request.getRequestURI().startsWith("/company")) {
            if (authorization != null) {
               DecodedJWT subjectToken = jwtProvider.validateToken(authorization);

                if (subjectToken == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                List<Object> roles = subjectToken.getClaim("roles").asList(Object.class);

                List<SimpleGrantedAuthority> listRolesPrefixed = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();

                request.setAttribute("company_id", subjectToken.getSubject());
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subjectToken.getSubject(), null, listRolesPrefixed);
                SecurityContextHolder.getContext().setAuthentication(auth);

            }
        }

        filterChain.doFilter(request, response);

    }
}
