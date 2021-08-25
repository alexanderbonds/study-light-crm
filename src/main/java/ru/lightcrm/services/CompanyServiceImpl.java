package ru.lightcrm.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lightcrm.entities.Company;
import ru.lightcrm.entities.Contact;
import ru.lightcrm.entities.Profile;
import ru.lightcrm.entities.dtos.CompanyDto;
import ru.lightcrm.entities.dtos.ContactDto;
import ru.lightcrm.exceptions.ResourceNotFoundException;
import ru.lightcrm.repositories.CompanyRepository;
import ru.lightcrm.repositories.ContactRepository;
import ru.lightcrm.services.interfaces.CompanyService;
import ru.lightcrm.services.interfaces.ProfileService;
import ru.lightcrm.services.interfaces.TaskService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companiesRepository;
    private final ContactRepository contactRepository;
    private final ProfileService profileService;
    private final TaskService taskService;

    public CompanyDto findByName(String name) {
        return new CompanyDto(companiesRepository.findOneByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Компания '%s' не найдена", name))));
    }

    public CompanyDto findByInn(Long inn) {
        return new CompanyDto(companiesRepository.findOneByInn(inn)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Компания с ИНН '%s' не найдена", inn))));
    }

    public CompanyDto findById(Long id) {
        return new CompanyDto(companiesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Компания с id '%s' не найдена", id))));
    }

    @Override
    public Company findEntityById(Long id) {
        return companiesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Компания с id '%s' не найдена", id)));
    }

    public List<CompanyDto> findAllDTO() {
        return companiesRepository.findAll().stream().map(c -> {
            CompanyDto companyDto = new CompanyDto(c);
            companyDto.setTasksCount(taskService.countByCompanyId(c.getId()));
            return companyDto;
        }).collect(Collectors.toList());
    }

    @Override
    public CompanyDto saveOrUpdate(CompanyDto companyDto) {
        Company company;
        if (companyDto.getId() == null) {
            company = new Company();
            company.setContacts(null);
            company.setComments(null);
        } else {
            company = companiesRepository.findById(companyDto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Компания с id '%s' не найдена", companyDto.getId())));
        }
        company.setName(companyDto.getName());
        company.setType(companyDto.isType());
        company.setInn(companyDto.getInn());
        company.setBillNumber(companyDto.getBillNumber());
        company.setPhoneNumber(companyDto.getPhoneNumber());
        company.setEmail(companyDto.getEmail());

        Set<Profile> managers = companyDto.getManagers().stream()
                .map(m -> profileService.findById(m.getId())).collect(Collectors.toSet());
        company.setManagers(managers);

        return new CompanyDto(companiesRepository.save(company));
    }

    @Override
    public void deleteById(Long id) {
        companiesRepository.deleteById(id);
    }

    @Override
    public void deleteContactById(Long id) {
        contactRepository.deleteById(id);
    }

    @Override
    public ContactDto saveOrUpdateContact(ContactDto contactDto) {
        Contact contact;
        if (contactDto.getId() == null) {
            contact = new Contact();
        } else {
            contact = contactRepository.findById(contactDto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Контакт с Id %s не найден.", contactDto.getId())));
        }
        contact.setName(contactDto.getName());
        contact.setDescription(contactDto.getDescription());
        contact.setEmail(contactDto.getEmail());
        contact.setPost(contactDto.getPost());
        contact.setPhone(contactDto.getPhone());
        contact.setCompany(findEntityById(contactDto.getCompanyId()));

        return new ContactDto(contactRepository.save(contact));
    }
}
