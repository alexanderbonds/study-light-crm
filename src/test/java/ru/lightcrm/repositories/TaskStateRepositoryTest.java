package ru.lightcrm.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.lightcrm.entities.TaskState;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class TaskStateRepositoryTest {

    private final String TEST_NAME = "В Работе";
    private final String TEST_NAME_ERROR = "не корректный статус";

    @Autowired
    private TaskStateRepository taskStateRepository;


    @Test
    public void findOneByNameTest() {
        Optional<TaskState> taskState = taskStateRepository.findOneByName(TEST_NAME);


        assertNotNull(taskState.get());
        assertTrue(taskState.get().getId() > 0);
        assertEquals(TEST_NAME, taskState.get().getName());
    }

    @Test
    public void testFindNoCorrectName() {
        assertThrows(NoSuchElementException.class, () -> taskStateRepository.findOneByName(TEST_NAME_ERROR).get(), "No value present");
        assertThrows(NoSuchElementException.class, () -> taskStateRepository.findOneByName(null).get(), "No value present");
    }
}
