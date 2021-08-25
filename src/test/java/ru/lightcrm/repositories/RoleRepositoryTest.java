package ru.lightcrm.repositories;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.lightcrm.entities.Role;

@DataJpaTest
@ActiveProfiles("test")
class RoleRepositoryTest {

  @Autowired
  private RoleRepository roleRepository;

  private final static String ROLE_NAME = "ROLE_MANAGER";
  private final static String ROLE_VISIBLE_NAME = "Менеджер";
  private final static String ROLE_NAME_NOT_EXIST = "ROLE_NOT_EXIST";


  @Test
  void findOneByNameTest() {
    Role roleRepo = roleRepository.findOneByName(ROLE_NAME).orElseGet(null);
    Assertions.assertAll(
        () -> Assertions.assertNotNull(roleRepo),
        () -> Assertions.assertEquals(ROLE_NAME, roleRepo.getName()),
        () -> Assertions.assertEquals(ROLE_VISIBLE_NAME, roleRepo.getVisibleName())
    );
  }

  @Test
  void findOneByNoExistOrNullName() {
    Assertions.assertAll(
        () -> Assertions.assertThrows(NoSuchElementException.class,
            () -> roleRepository.findOneByName(ROLE_NAME_NOT_EXIST).get(), "No value present"),
        () -> Assertions.assertThrows(NoSuchElementException.class,
            () -> roleRepository.findOneByName(null).get(), "No value present")
    );
  }
}