package ru.lightcrm.controllers.interfaces;

import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.StringWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.lightcrm.exceptions.ResourceNotFoundException;
import ru.lightcrm.services.interfaces.UserService;
import ru.lightcrm.utils.JwtRequest;
import ru.lightcrm.utils.JwtResponse;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  private static StringWriter writer;
  private static JwtRequest request;

  public static String USERNAME = "Bob";


  @BeforeAll
  static void setUp() throws IOException {
    writer = new StringWriter();
    ObjectMapper mapper = new ObjectMapper();
    request = new JwtRequest(USERNAME, "bob");
    mapper.writeValue(writer, request);
  }

  @Test
  void createAuthToken() throws Exception {
    given(userService.createAuthToken(request)).willReturn(new JwtResponse(null));
    mockMvc.perform(MockMvcRequestBuilders.post("/auth")
        .contentType(MediaType.APPLICATION_JSON)
        .content(writer.toString()))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void createAuthTokenStatus404() throws Exception {
    given(userService.createAuthToken(request))
        .willThrow(
            new ResourceNotFoundException(String.format("Пользователь '%s' не найден", USERNAME))
        );

    mockMvc.perform(MockMvcRequestBuilders.post("/auth")
        .contentType(MediaType.APPLICATION_JSON)
        .content(writer.toString()))
        .andExpect(MockMvcResultMatchers.status().is(404));
  }
}