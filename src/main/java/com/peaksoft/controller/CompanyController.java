package com.peaksoft.controller;

import com.peaksoft.entity.Company;
import com.peaksoft.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/getAllCompany")
    public String getAllCompanies(Model model){
        model.addAttribute("companies",companyService.getAllCompany());
        return "/companies/mainCompanyPage";
    }

    @GetMapping("/getCompanyById/{id}")
    public String getCompanyById(@PathVariable Long id, Model model) {
        model.addAttribute("company", companyService.getCompanyById(id));
        return "redirect:/getAllCompany";
    }

    @GetMapping("/new")
    public String newCompany(Model model) {
        model.addAttribute("newCompany", new Company());
        return "/companies/newCompany";
    }

    @PostMapping("/save")
    public String saveCompany(@ModelAttribute("newCompany") Company company) {
        companyService.saveCompany(company);
        return "redirect:/getAllCompany";
    }




    @GetMapping("/updateCompany/{id}")
    public String updateCompany(@PathVariable Long id, Model model) {
        Company company = companyService.getCompanyById(id);
        model.addAttribute("company", company);
        model.addAttribute("id", company.getId());
        return "/companies/updateCompany";
    }

    @PostMapping("/{id}/saveUpdateCompany")
    public String saveUpdateCompany(@PathVariable Long id, @ModelAttribute("company") Company company) {
        companyService.updateCompany(id, company);
        return "redirect:/getAllCompany";
    }

    @GetMapping("/deleteCompany")
    public String deleteCompany(@RequestParam("companyId") Long id) {
        companyService.deleteCompany(companyService.getCompanyById(id));
        return "redirect:/getAllCompany";
    }



}

