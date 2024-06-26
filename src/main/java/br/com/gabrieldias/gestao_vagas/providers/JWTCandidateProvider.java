package br.com.gabrieldias.gestao_vagas.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class JWTCandidateProvider {

    @Value("${user.security.token.secret}")
    public String secretCandidateKey;

    public DecodedJWT validateCandidateToken(String token) {
        token = token.replace("Bearer ", "");

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretCandidateKey);

            DecodedJWT tokenValidated = JWT.require(algorithm).build().verify(token);

            return tokenValidated;
        } catch (JWTVerificationException e) {
            return null;
        }
    }

}
