package ru.lightcrm.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ru.lightcrm.entities.Profile;
import ru.lightcrm.entities.User;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ProfileRepositoryTest {

  private static final long TEST_USER_ID = 2L;
  private static final String TEST_NONEXISTENT_USER_LOGIN = "fake_login";
  private static final long TEST_NONEXISTENT_USER_ID = 99999L;

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ProfileRepository profileRepository;

  @Test
  public void findByLoginTest() {
    User user = entityManager.find(User.class, TEST_USER_ID);

    Optional<Profile> profile = profileRepository.findByLogin(user.getLogin());

    assertNotNull(profile.get());
    assertTrue(profile.get().getId() > 0);
    assertEquals(TEST_USER_ID, profile.get().getUser().getId());
  }

  @Test
  public void throwErrorInFindByLoginTest() {
    Optional<Profile> profile = profileRepository.findByLogin(TEST_NONEXISTENT_USER_LOGIN);

    assertThrows(NoSuchElementException.class, profile::get);
  }

  @Test
  public void findByUserIdTest() {
    Optional<Profile> profile = profileRepository.findByUserId(TEST_USER_ID);

    assertNotNull(profile.get());
    assertTrue(profile.get().getId() > 0);
    assertEquals(TEST_USER_ID, profile.get().getUser().getId());
  }

  @Test
  public void throwErrorInFindByIdTest() {
    Optional<Profile> profile = profileRepository.findByUserId(TEST_NONEXISTENT_USER_ID);

    assertThrows(NoSuchElementException.class, profile::get);
  }
}
