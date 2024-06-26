package br.com.gabrieldias.gestao_vagas.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTProvider {

    @Value("${security.token.secret}")
    private String secretKey;

    public DecodedJWT validateToken(String token) {
        token = token.replace("Bearer ", "");
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            DecodedJWT subject = JWT.require(algorithm).build().verify(token);
            return subject;
        } catch (JWTVerificationException e) {
            return null;
        }
    }

}
