package ru.lightcrm.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.lightcrm.entities.Company;
import ru.lightcrm.entities.Department;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CompanyRepositoryTest {

    private final String TEST_COMPANY_NAME = "ООО Вектор";
    private final Long TEST_COMPANY_INN = 58758758964L;
    private final Long TEST_COMPANY_ID = 1L;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void findOneByName() {
        Optional<Company> company = companyRepository.findOneByName(TEST_COMPANY_NAME);
        assertNotNull(company.get());
        assertTrue(company.get().getId() == TEST_COMPANY_ID);
        assertEquals(TEST_COMPANY_NAME, company.get().getName());
        assertEquals(TEST_COMPANY_INN, company.get().getInn());
    }

    @Test
    void findOneByInn() {
        Optional<Company> company = companyRepository.findOneByInn(TEST_COMPANY_INN);
        assertNotNull(company.get());
        assertTrue(company.get().getId() == TEST_COMPANY_ID);
        assertEquals(TEST_COMPANY_NAME, company.get().getName());
        assertEquals(TEST_COMPANY_INN, company.get().getInn());
    }

    @Test
    void findOneById() {
        Optional<Company> company = companyRepository.findOneById(TEST_COMPANY_ID);
        assertNotNull(company.get());
        assertTrue(company.get().getId() == TEST_COMPANY_ID);
        assertEquals(TEST_COMPANY_NAME, company.get().getName());
        assertEquals(TEST_COMPANY_INN, company.get().getInn());
    }
}