package ru.lightcrm.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.lightcrm.entities.Department;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DepartmentRepositoryTest {

    private final String TEST_DEPARTMENT_NAME = "IT-отдел";
    private final Long TEST_LEADER_ID = 1L;

    @Autowired
    private DepartmentRepository departmentRepository;


    @Test
    void findOneByName() {
        Optional<Department> department = departmentRepository.findOneByName(TEST_DEPARTMENT_NAME);
        assertNotNull(department.get());
        assertTrue(department.get().getId() > 0);
        assertEquals(TEST_DEPARTMENT_NAME, department.get().getName());
    }

    @Test
    void findOneByLeaderId() {
        Optional<Department> department = departmentRepository.findOneByLeaderId(TEST_LEADER_ID);
        assertNotNull(department.get());
        assertTrue(department.get().getId() > 0);
        assertEquals(TEST_DEPARTMENT_NAME, department.get().getName());
    }
}