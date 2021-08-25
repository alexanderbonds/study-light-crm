package ru.lightcrm.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.lightcrm.entities.Department;
import ru.lightcrm.entities.Profile;
import ru.lightcrm.entities.dtos.DepartmentDto;
import ru.lightcrm.repositories.DepartmentRepository;
import ru.lightcrm.services.interfaces.DepartmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class DepartmentServiceTest {
    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    private static final String DEPARTMENT_NAME = "Проект";
    private static final String DEPARTMENT_DESCRIPTION = "Описание проекта";
    private static final int DEPARTMENT_COUNT = 2;

    private Optional<Department> initMockDepartment(Long id) {
        Department project = new Department();
        project.setId(id);
        project.setName(DEPARTMENT_NAME + " " + id);
        project.setDescription(DEPARTMENT_DESCRIPTION + " " + id);

        Profile profile = new Profile();
        profile.setId(1L);

        project.setLeader(profile);

        return Optional.of(project);
    }

    private List<Department> initMockDepartmentList(){
        List<Department> projectList = new ArrayList<>();
        for(int i = 0; i < DEPARTMENT_COUNT; i++) projectList.add(initMockDepartment((long)(i + 1)).orElse(null));

        return projectList;
    }

    @Test
    public void findAll() {
        Mockito.doReturn(initMockDepartmentList()).when(departmentRepository).findAll();

        List<DepartmentDto> departmentDTOList = departmentService.findAll();
        Assertions.assertNotNull(departmentDTOList);
        Assertions.assertEquals(DEPARTMENT_COUNT, departmentDTOList.size());
        Assertions.assertEquals(DEPARTMENT_NAME + " 1", departmentDTOList.get(0).getName());
        Assertions.assertEquals(1, departmentDTOList.get(DEPARTMENT_COUNT - 1).getLeaderId());
    }

    @Test
    public void findById() {
        Long id = 1L;
        Mockito.doReturn(initMockDepartment(id)).when(departmentRepository).findById(id);

        DepartmentDto departmentDTO = departmentService.findDtoById(id);
        Assertions.assertNotNull(departmentDTO);
        Assertions.assertEquals(DEPARTMENT_NAME + " " + id, departmentDTO.getName());
        Assertions.assertEquals(DEPARTMENT_DESCRIPTION + " " + id, departmentDTO.getDescription());
        Assertions.assertEquals(1L, departmentDTO.getLeaderId());
    }

    @Test
    public void findOneByName() {
        Long id = 1L;
        final String name = DEPARTMENT_NAME + " " + id;
        Mockito.doReturn(initMockDepartment(id)).when(departmentRepository).findOneByName(name);

        DepartmentDto departmentDTO = departmentService.findOneDtoByName(name);
        Assertions.assertNotNull(departmentDTO);
        Assertions.assertEquals(id, departmentDTO.getId());
        Assertions.assertEquals(DEPARTMENT_DESCRIPTION + " " + id, departmentDTO.getDescription());
        Assertions.assertEquals(1, departmentDTO.getLeaderId());
    }

    @Test
    public void findOneByLeaderId() {
        Long id = 1L;
        Mockito.doReturn(initMockDepartment(id)).when(departmentRepository).findOneByLeaderId(id);

        DepartmentDto departmentDTO = departmentService.findOneByLeaderId(id);
        Assertions.assertNotNull(departmentDTO);
        Assertions.assertEquals(DEPARTMENT_NAME + " " + id, departmentDTO.getName());
        Assertions.assertEquals(DEPARTMENT_DESCRIPTION + " " + id, departmentDTO.getDescription());
        Assertions.assertEquals(1L, departmentDTO.getLeaderId());
    }
}
