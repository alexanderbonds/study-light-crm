package ru.lightcrm.controllers.interfaces;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.lightcrm.services.interfaces.DepartmentService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DepartmentControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private DepartmentService departmentService;
  private final static List<String> departmentNames = List.of("department1", "department2");


  @Test
  @WithMockUser(username = "Bob", authorities = "ADMIN")
  void getAllDepartmentNames() throws Exception {
    given(departmentService.getAllDepartmentNames()).willReturn(departmentNames);

    mvc.perform(get("/api/v1/departments/names")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0]", is("department1")))
        .andExpect(jsonPath("$[1]", is("department2")));
  }
}
