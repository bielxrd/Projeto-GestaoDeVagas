package br.com.gabrieldias.gestao_vagas.modules.company.useCases;

import br.com.gabrieldias.gestao_vagas.exceptions.UserFoundException;
import br.com.gabrieldias.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.gabrieldias.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity) {

        Optional<CompanyEntity> company = this.companyRepository.findByUsernameOrEmailOrName(companyEntity.getUsername(), companyEntity.getEmail(), companyEntity.getName());

        if (company.isPresent()) {
            throw new UserFoundException("Empresa já cadastrada.");
        }

        String companyEncoded = passwordEncoder.encode(companyEntity.getPassword());

        companyEntity.setPassword(companyEncoded);

        return this.companyRepository.save(companyEntity);
    }


}
