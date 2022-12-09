package com.peaksoft.service.serviceimpl;

import com.peaksoft.entity.Company;
import com.peaksoft.repository.CompanyRepository;
import com.peaksoft.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).get();
    }

    @Override
    public void saveCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public void updateCompany(Long id, Company company) {
        Company company1 = companyRepository.findById(id).get();
        company1.setCompanyName(company.getCompanyName());
        company1.setLocatedCountry(company.getLocatedCountry());
        companyRepository.save(company1);
    }

    @Override
    public void deleteCompany(Company company) {
        companyRepository.delete(company);
    }

}
