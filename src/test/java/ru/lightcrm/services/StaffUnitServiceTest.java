package ru.lightcrm.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.lightcrm.entities.*;
import ru.lightcrm.entities.dtos.StaffUnitDto;
import ru.lightcrm.repositories.StaffUnitRepository;
import ru.lightcrm.services.interfaces.StaffUnitService;

import java.util.*;


@SpringBootTest
@ActiveProfiles("test")
public class StaffUnitServiceTest {
    @Autowired
    private StaffUnitService staffUnitService;

    @MockBean
    private StaffUnitRepository staffUnitRepository;

    private static final String STAFF_UNIT_NAME = "Директор";
    private static final int STAFF_UNIT_COUNT = 3;
    private static StaffUnit staffUnitNew;
    private static StaffUnit staffUnitTest;


    @BeforeEach
    public void init() {
        StaffUnit test1 = new StaffUnit();
        test1.setId(1L);
        test1.setName("Директор");
        StaffUnit test2 = new StaffUnit();
        test2.setId(2L);
        test2.setName("Менеджер");
        StaffUnit test3 = new StaffUnit();
        test3.setId(3L);
        test3.setName("Бухгалтер");

        List<StaffUnit> names = new ArrayList<>();
        names.add(test1);
        names.add(test2);
        names.add(test3);

        //create new
        staffUnitNew = new StaffUnit();
        staffUnitNew.setName("Директор");

        //update
        staffUnitTest = new StaffUnit();
        staffUnitTest.setId(1L);
        staffUnitTest.setName("Директор NEW");
        staffUnitTest.setRoles(null);

        Mockito.doReturn(names).when(staffUnitRepository).findAll();
        Mockito.doReturn(Optional.of(test1)).when(staffUnitRepository).findOneByName(STAFF_UNIT_NAME);
        Mockito.doNothing().when(staffUnitRepository).deleteById(test3.getId());
        Mockito.doAnswer(invocation -> {
            names.remove((int) (test3.getId() - 1));
            return null;
        }).when(staffUnitRepository).deleteById(test3.getId());
        Mockito.doReturn(test1).when(staffUnitRepository).save(staffUnitNew);
        Mockito.doReturn(staffUnitTest).when(staffUnitRepository).save(staffUnitTest);
        Mockito.doReturn(Optional.of(staffUnitTest)).when(staffUnitRepository).findOneByName(STAFF_UNIT_NAME + " NEW");
    }

    @Test
    void getNamesTest() {
        List<String> names = staffUnitService.getNames();
        Assertions.assertNotNull(names);
        Assertions.assertEquals(3, names.size());
        Assertions.assertEquals("Директор", names.get(0));
        Assertions.assertEquals("Менеджер", names.get(1));
        Assertions.assertEquals("Бухгалтер", names.get(2));
    }

    @Test
    void findByNameTest() {
        StaffUnit staffUnit = staffUnitService.findByName("Директор");
        Assertions.assertNotNull(staffUnit);
        Assertions.assertEquals(STAFF_UNIT_NAME, staffUnit.getName());
    }

    @Test
    void findDtoByNameTest() {
        StaffUnitDto staffUnitDto = staffUnitService.findDtoByName("Директор");
        Assertions.assertNotNull(staffUnitDto);
        Assertions.assertEquals(STAFF_UNIT_NAME, staffUnitDto.getName());
    }

    @Test
    public void deleteByIdTest() {
        Long id = 3L;
        Assertions.assertEquals(STAFF_UNIT_COUNT, staffUnitService.getNames().size());
        staffUnitService.deleteById(id);
        Assertions.assertEquals(STAFF_UNIT_COUNT - 1, staffUnitService.getNames().size());
    }

    @Test
    public void create() {
        StaffUnitDto staffUnitDto = new StaffUnitDto(staffUnitNew);
        StaffUnitDto result = staffUnitService.saveOrUpdate(staffUnitDto);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(STAFF_UNIT_NAME, result.getName());

    }

    @Test
    public void update() {
        StaffUnitDto staffUnitDto = new StaffUnitDto(staffUnitTest);
        StaffUnitDto result = staffUnitService.saveOrUpdate(staffUnitDto);

        Assertions.assertEquals(staffUnitDto.getId(), result.getId());
        Assertions.assertEquals(staffUnitDto.getName(), result.getName());
        Assertions.assertEquals(staffUnitDto.getRoles(), result.getRoles());
        Mockito.verify(staffUnitRepository, Mockito.times(1)).save(staffUnitTest);

    }
}
