package br.com.gabrieldias.gestao_vagas.modules.company.useCases;

import br.com.gabrieldias.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.gabrieldias.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.gabrieldias.gestao_vagas.modules.company.repositories.CompanyRepository;
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
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        Optional<CompanyEntity> company = this.companyRepository.findByUsername(authCompanyDTO.getUsername());

        if (company.isPresent()) {
            boolean matches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.get().getPassword());

            if (!matches) {
                throw new AuthenticationException("Username/Password incorrect.");
            }

            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            String token = JWT.create().withIssuer("Javagas").withSubject(company.get().getId().toString()).sign(algorithm); // Necessario passar para String o Subject do JWT.

            return token;

        } else {
            throw new UsernameNotFoundException("Username/password incorrect.");
        }


    }


}
