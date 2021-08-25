package ru.lightcrm.repositories;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.lightcrm.entities.StaffUnit;

@DataJpaTest
@ActiveProfiles("test")
class StaffUnitRepositoryTest {

  @Autowired
  private StaffUnitRepository staffUnitRepository;

  private final static String STAFF_UNIT_NAME = "Начальник IT-департамента";
  private final static String STAFF_UNIT_NOT_EXIST = "Несуществующая должность";

  @Test
  void findOneByNameTest() {
    StaffUnit repoUnit = staffUnitRepository.findOneByName(STAFF_UNIT_NAME).orElseGet(null);
    Assertions.assertAll(
        () -> Assertions.assertNotNull(repoUnit),
        () -> Assertions.assertEquals(STAFF_UNIT_NAME, repoUnit.getName())
    );
  }

  @Test
  void findOneByNoExistOrNullName() {
    Assertions.assertAll(
        () -> Assertions.assertThrows(NoSuchElementException.class,
            () -> staffUnitRepository.findOneByName(STAFF_UNIT_NOT_EXIST).get(),"No value present"),
        () -> Assertions.assertThrows(NoSuchElementException.class,
            () -> staffUnitRepository.findOneByName(null).get(), "No value present")
    );
  }
}