package br.com.gabrieldias.gestao_vagas.modules.candidate.useCases;

import br.com.gabrieldias.gestao_vagas.modules.candidate.dto.AuthCandidateDTO;
import br.com.gabrieldias.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.gabrieldias.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.gabrieldias.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;


@Service
public class AuthCandidateUseCase {

    @Value("${user.security.token.secret}")
    private String secretKey;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO authenticate(AuthCandidateDTO authCandidateDTO) throws AuthenticationException {

        Optional<CandidateEntity> candidateFound = candidateRepository.findByUsername(authCandidateDTO.getUsername());

        if (candidateFound.isPresent()) {
            boolean matches = passwordEncoder.matches(authCandidateDTO.getPassword(), candidateFound.get().getPassword());

            if (!matches) {
                throw new AuthenticationException("Username/password incorrect.");
            }

            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            String token = JWT.create().withIssuer("LOGUSER")
                    .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                    .withClaim("roles", Arrays.asList("candidate"))
                    .withSubject(candidateFound.get().getId().toString())
                    .sign(algorithm);

            return AuthCandidateResponseDTO.builder().acess_token(token).build();
        } else {
            throw new UsernameNotFoundException("Username/password incorrect.");
        }

    }


}
