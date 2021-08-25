package ru.lightcrm.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.lightcrm.entities.Task;

import java.util.List;

@ActiveProfiles("test")
@DataJpaTest
public class TaskRepositoryTest {

    private static final Long TEST_PROJECT_ID = 1L;
    private static final Long TEST_PRODUCER_ID = 2L;
    private static final Long TEST_RESPONSIBLE_ID = 4L;
    private static final Long TEST_TASKSTATE_ID = 1L;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void findByProducerIdTest(){
        List<Task> taskList = taskRepository.findByProducerId(TEST_PRODUCER_ID);
        Assertions.assertNotNull(taskList);
        for (int i = 0; i < taskList.size(); i++)
            Assertions.assertEquals(TEST_PRODUCER_ID, taskList.get(i).getProducer().getId());
    }

    @Test
    public void findByProducerIdAndTaskStateIdTest(){
        List<Task> taskList = taskRepository.findByProducerIdAndTaskStateId(TEST_PRODUCER_ID, TEST_TASKSTATE_ID);
        Assertions.assertNotNull(taskList);
        for (int i = 0; i < taskList.size(); i++) {
            Assertions.assertEquals(TEST_PRODUCER_ID, taskList.get(i).getProducer().getId());
            Assertions.assertEquals(TEST_TASKSTATE_ID, taskList.get(i).getTaskState().getId());
        }
    }

    @Test
    public void findByResponsibleIdTest(){
        List<Task> taskList = taskRepository.findByResponsibleId(TEST_RESPONSIBLE_ID);
        Assertions.assertNotNull(taskList);
        for (int i = 0; i < taskList.size(); i++)
            Assertions.assertEquals(TEST_RESPONSIBLE_ID, taskList.get(i).getResponsible().getId());
    }

    @Test
    public void findByResponsibleIdAndTaskStateIdTest(){
        List<Task> taskList = taskRepository.findByResponsibleIdAndTaskStateId(TEST_RESPONSIBLE_ID, TEST_TASKSTATE_ID);
        Assertions.assertNotNull(taskList);
        for (int i = 0; i < taskList.size(); i++) {
            Assertions.assertEquals(TEST_RESPONSIBLE_ID, taskList.get(i).getResponsible().getId());
            Assertions.assertEquals(TEST_TASKSTATE_ID, taskList.get(i).getTaskState().getId());
        }
    }

    @Test
    public void findByProjectIdTest(){
        List<Task> taskList = taskRepository.findByProjectId(TEST_PROJECT_ID);
        Assertions.assertNotNull(taskList);
        for (int i = 0; i < taskList.size(); i++)
            Assertions.assertEquals(TEST_PROJECT_ID, taskList.get(i).getProject().getId());
    }
}
