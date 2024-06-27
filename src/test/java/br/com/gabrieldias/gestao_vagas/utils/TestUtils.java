package br.com.gabrieldias.gestao_vagas.utils;

import br.com.gabrieldias.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

public class TestUtils {

    @SneakyThrows
    public static String objectToJson(Object object) {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static String generateTokenTest(UUID idCompany, String keyValue) {
        Algorithm algorithm = Algorithm.HMAC256(keyValue);
        Instant expiresIn = Instant.now().plus(Duration.ofHours(2));
        return JWT.create().withIssuer("Javagas").withExpiresAt(expiresIn)
                .withSubject(idCompany.toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);
    }

    public static AuthCandidateResponseDTO generateAuthCandidateResponseDTO() {
        Algorithm algorithm = Algorithm.HMAC256("USUARIOVAGAS_@123#");
        Instant expiresIn = Instant.now().plus(Duration.ofHours(2));
        String token = JWT.create().withIssuer("LOGUSER")
                .withExpiresAt(expiresIn)
                .withClaim("roles", Arrays.asList("CANDIDATE"))
                .withSubject(UUID.randomUUID().toString())
                .sign(algorithm);

        return AuthCandidateResponseDTO.builder().acess_token(token).expiresIn(expiresIn.toEpochMilli()).build();
    }

}
