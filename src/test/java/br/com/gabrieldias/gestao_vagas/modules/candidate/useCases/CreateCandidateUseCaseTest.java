package br.com.gabrieldias.gestao_vagas.modules.candidate.useCases;

import br.com.gabrieldias.gestao_vagas.exceptions.UserFoundException;
import br.com.gabrieldias.gestao_vagas.exceptions.UserNotFoundException;
import br.com.gabrieldias.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.gabrieldias.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateCandidateUseCaseTest {

    @InjectMocks
    private CreateCandidateUseCase createCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Should not be able to create candidate with username and email already registered")
    public void shouldNotBeAbleCreateCandidateWithUsernameAndEmailAlreadyRegistered() {
        CandidateEntity candidate = new CandidateEntity();
        candidate.setUsername("bielxrd");
        candidate.setEmail("gabriel.dias@gmail.com");

        when(candidateRepository.findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())).thenReturn(Optional.of(candidate));

        try {
            createCandidateUseCase.execute(candidate);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserFoundException.class);
        }

    }

    @Test
    @DisplayName("Should be able to create an candidate")
    public void mustBeAbleToCreateAnCandidate() {
        CandidateEntity candidate = new CandidateEntity();
        candidate.setId(UUID.randomUUID());
        candidate.setUsername("bielxrd");
        candidate.setName("Gabriel");
        candidate.setEmail("gabriel.dias@gmail.com");
        candidate.setPassword("x123opgrvmn");
        candidate.setDescription("PESSOA DESENVOLVEDORA JAVA");

        when(candidateRepository.findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(candidate.getPassword())).thenReturn("encodedPassword");
        when(candidateRepository.save(candidate)).thenReturn(candidate);



        try {
            CandidateEntity execute = createCandidateUseCase.execute(candidate);
            assertNotNull(execute.getId());
            assertEquals(candidate.getUsername(), execute.getUsername());
            assertEquals(candidate.getName(), execute.getName());
            assertEquals(candidate.getEmail(), execute.getEmail());
            assertEquals(candidate.getDescription(), execute.getDescription());
            assertEquals(candidate.getPassword(), execute.getPassword());
        } catch (Exception e) {
            fail("Exception thrown: "+e.getMessage());
        }
    }

    @Test
    @DisplayName("The password should be encoded")
    public void shouldEncodeThePassword() {

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        CandidateEntity candidate = new CandidateEntity();
        candidate.setPassword("x551209420");

        String encodedPassword = encoder.encode(candidate.getPassword());

        try {
            assertNotEquals(candidate.getPassword(), encodedPassword);

            boolean matches = encoder.matches(candidate.getPassword(), encodedPassword);
            assertTrue(matches);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

}
