package br.com.gabrieldias.gestao_vagas.modules.candidate.useCases;

import br.com.gabrieldias.gestao_vagas.modules.candidate.dto.AuthCandidateDTO;
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
import java.util.Optional;


@Service
public class AuthCandidateUseCase {

    @Value("${user.security.token.secret}")
    private String secretKey;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticate(AuthCandidateDTO authCandidateDTO) throws AuthenticationException {

        Optional<CandidateEntity> candidateFound = candidateRepository.findByUsername(authCandidateDTO.getUsername());

        if (candidateFound.isPresent()) {
            boolean matches = passwordEncoder.matches(authCandidateDTO.getPassword(), candidateFound.get().getPassword());

            if (!matches) {
                throw new AuthenticationException("Username/password incorrect.");
            }

            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create().withIssuer("LOGUSER").withSubject(candidateFound.get().getId().toString()).sign(algorithm);
        } else {
            throw new UsernameNotFoundException("Username/password incorrect.");
        }

    }


}
