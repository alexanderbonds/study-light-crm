package ru.lightcrm.services.interfaces;

import ru.lightcrm.entities.Company;
import ru.lightcrm.entities.dtos.CompanyDto;
import ru.lightcrm.entities.dtos.ContactDto;

import java.util.List;

public interface CompanyService {
    CompanyDto findByName(String name);
    CompanyDto findByInn(Long inn);
    CompanyDto findById(Long id);
    Company findEntityById(Long id);
    List<CompanyDto> findAllDTO();
    CompanyDto saveOrUpdate(CompanyDto companyDto);
    void deleteById(Long id);
    void deleteContactById(Long id);
    ContactDto saveOrUpdateContact(ContactDto contactDto);
}
