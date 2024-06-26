package br.com.gabrieldias.gestao_vagas.modules.company.useCases;


import br.com.gabrieldias.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.gabrieldias.gestao_vagas.modules.company.entities.JobEntity;
import br.com.gabrieldias.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.gabrieldias.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public JobEntity execute(JobEntity jobEntity) {

        companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(() -> new CompanyNotFoundException("Company not found"));

        return this.jobRepository.save(jobEntity);
    }

}
