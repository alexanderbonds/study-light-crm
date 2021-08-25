package ru.lightcrm.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.lightcrm.entities.Project;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ProjectRepositoryTest {

  private static final String TEST_PROJECT_NAME = "Проект 1";
  private static final Long TEST_MANAGER_ID = 2L;

  @Autowired
  private ProjectRepository projectRepository;

  @Test
  public void findOneByNameTest() {
    Optional<Project> project = projectRepository.findOneByName(TEST_PROJECT_NAME);

    assertNotNull(project.get());
    assertTrue(project.get().getId() > 0);
    assertEquals(TEST_PROJECT_NAME, project.get().getName());
  }

  @Test
  public void findByManagerIdTest() {
    List<Project> projectList = projectRepository.findByManagerId(TEST_MANAGER_ID);
    Assertions.assertNotNull(projectList);
    Assertions.assertTrue(projectList.size() > 0);
    for (int i = 0; i < projectList.size(); i++)
      Assertions.assertEquals(TEST_MANAGER_ID, projectList.get(i).getManager().getId().intValue());
  }
}
