package com.peaksoft.service;

import com.peaksoft.entity.Company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompany();
    Company getCompanyById(Long id);
    void saveCompany(Company company);
    void updateCompany(Long id, Company company);
    void deleteCompany(Company company);
}
