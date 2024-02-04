package br.com.gabrieldias.gestao_vagas.modules.company.useCases;

import br.com.gabrieldias.gestao_vagas.exceptions.UserFoundException;
import br.com.gabrieldias.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.gabrieldias.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity execute(CompanyEntity companyEntity) {

        this.companyRepository
                .findByUsernameOrEmailOrName(companyEntity.getUsername(), companyEntity.getEmail(), companyEntity.getName())
                .ifPresent((user) ->{
                    throw new UserFoundException();
                });

        return this.companyRepository.save(companyEntity);
    }


}
