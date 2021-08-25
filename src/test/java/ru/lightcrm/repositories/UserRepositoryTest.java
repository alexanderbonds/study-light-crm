package ru.lightcrm.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.lightcrm.entities.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

  private final String TEST_LOGIN = "admin";

  @Autowired
  private UserRepository userRepository;


  @Test
  public void findByLoginTest() {
    Optional<User> user = userRepository.findByLogin(TEST_LOGIN);

    assertNotNull(user.orElse(null));
    assertTrue(user.get().getId() > 0);
    assertEquals(TEST_LOGIN, user.get().getLogin().toLowerCase());
  }

  @Test
  public void existsByLoginTest() {
    assertTrue(userRepository.existsByLogin(TEST_LOGIN));
  }
}
