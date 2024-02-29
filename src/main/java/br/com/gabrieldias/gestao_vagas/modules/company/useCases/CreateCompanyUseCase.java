package br.com.gabrieldias.gestao_vagas.modules.company.useCases;

import br.com.gabrieldias.gestao_vagas.exceptions.UserFoundException;
import br.com.gabrieldias.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.gabrieldias.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity execute(CompanyEntity companyEntity) {

        Optional<CompanyEntity> company = this.companyRepository.findByUsernameOrEmailOrName(companyEntity.getUsername(), companyEntity.getEmail(), companyEntity.getName());

        if (company.isPresent()) {
            throw new UserFoundException();
        }

        return this.companyRepository.save(companyEntity);
    }


}
