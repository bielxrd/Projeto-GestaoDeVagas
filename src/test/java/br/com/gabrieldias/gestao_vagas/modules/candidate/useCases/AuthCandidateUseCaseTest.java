package br.com.gabrieldias.gestao_vagas.modules.candidate.useCases;

import br.com.gabrieldias.gestao_vagas.exceptions.AuthenticationExceptionImpl;
import br.com.gabrieldias.gestao_vagas.modules.candidate.dto.AuthCandidateDTO;
import br.com.gabrieldias.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.gabrieldias.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.gabrieldias.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.gabrieldias.gestao_vagas.utils.TestUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import javax.naming.AuthenticationException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthCandidateUseCaseTest {

    @InjectMocks
    private AuthCandidateUseCase authCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(authCandidateUseCase, "secretKey", "USUARIOVAGAS_@123#");
    }


    @Test
    @DisplayName("Should not be able to authenticate with username non-existent")
    public void should_not_be_able_to_authenticate_with_username_that_not_exists() {
        AuthCandidateDTO authCandidateDTO = new AuthCandidateDTO("pedrozardetti", "1leticia2@");

        try {
            this.authCandidateUseCase.authenticate(authCandidateDTO);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UsernameNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to authenticate with password incorrect.")
    public void should_not_be_able_to_authenticate_with_password_incorrect() {
        AuthCandidateDTO authCandidateDTO = new AuthCandidateDTO("pedrozardetti", "1leticia2@");

        Optional<CandidateEntity> candidate = Optional.of(CandidateEntity.builder()
                .username(authCandidateDTO.getUsername())
                .password(this.passwordEncoder.encode(authCandidateDTO.getPassword()))
                .build());

        when(this.candidateRepository.findByUsername(authCandidateDTO.getUsername())).thenReturn(candidate);

        when(passwordEncoder.matches(authCandidateDTO.getPassword(), candidate.get().getPassword())).thenReturn(false);

        try {
            this.authCandidateUseCase.authenticate(authCandidateDTO);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(AuthenticationExceptionImpl.class);
        }

    }

    @Test
    @DisplayName("Should be able to authenticate with success.")
    public void should_be_able_to_authenticate_with_success() throws AuthenticationException {
        AuthCandidateDTO authCandidateDTO = new AuthCandidateDTO("pedrozardetti", "1leticia2@");

        Optional<CandidateEntity> candidate = Optional.of(CandidateEntity.builder()
                .id(UUID.randomUUID())
                .username(authCandidateDTO.getUsername())
                .password(this.passwordEncoder.encode(authCandidateDTO.getPassword()))
                .build());

        when(this.candidateRepository.findByUsername(authCandidateDTO.getUsername())).thenReturn(candidate);

        when(passwordEncoder.matches(authCandidateDTO.getPassword(), candidate.get().getPassword())).thenReturn(true);

        AuthCandidateResponseDTO response = TestUtils.generateAuthCandidateResponseDTO();

        Algorithm algorithm = Algorithm.HMAC256("USUARIOVAGAS_@123#");

        AuthCandidateResponseDTO result = authCandidateUseCase.authenticate(authCandidateDTO);

        DecodedJWT subjectMock = JWT.require(algorithm).build().verify(response.getAcess_token());
        DecodedJWT subjectMockFroMService = JWT.require(algorithm).build().verify(result.getAcess_token());

        assertEquals(subjectMock.getSubject(), subjectMock.getSubject());


    }

}
