package br.com.gabrieldias.gestao_vagas.security;

import br.com.gabrieldias.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecuriyFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//      SecurityContextHolder.getContext().setAuthentication(null);
        String authorization = request.getHeader("Authorization");

        if(request.getRequestURI().startsWith("/company")) {
            if (authorization != null) {
                String subjectToken = jwtProvider.validateToken(authorization);

                if (subjectToken.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                request.setAttribute("company_id", subjectToken);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subjectToken, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.println(subjectToken);
            }
        }

        filterChain.doFilter(request, response);

    }
}
