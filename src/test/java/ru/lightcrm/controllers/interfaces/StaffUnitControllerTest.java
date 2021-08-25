package ru.lightcrm.controllers.interfaces;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.lightcrm.entities.Role;
import ru.lightcrm.entities.StaffUnit;
import ru.lightcrm.entities.dtos.ProfileDto;
import ru.lightcrm.entities.dtos.ProjectDto;
import ru.lightcrm.entities.dtos.StaffUnitDto;
import ru.lightcrm.entities.dtos.TaskDto;
import ru.lightcrm.services.interfaces.StaffUnitService;
import ru.lightcrm.services.interfaces.TaskService;
import ru.lightcrm.utils.JwtRequest;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StaffUnitControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StaffUnitService staffUnitService;

    private static List<String> unitNames;
    private static StaffUnitDto testStaffUnitDto;
    private static StaffUnitDto testNewStaffUnitDto;
    private static StaffUnitDto testReturnedNewStaffUnitDto;
    private static StaffUnitDto staffUnitModified;

    private StringWriter writer;
    private ObjectMapper mapper;


    @BeforeAll
    public static void init() {

        StaffUnit staffUnit1 = new StaffUnit();
        staffUnit1.setId(1L);
        staffUnit1.setName("unitName1");
        testStaffUnitDto = new StaffUnitDto(staffUnit1);

        unitNames = List.of(testStaffUnitDto.getName());

        // saveTest
        StaffUnit staffUnit2 = new StaffUnit();
        staffUnit2.setName("unitName2");
        staffUnit2.setRoles(new HashSet<>());
        testNewStaffUnitDto = new StaffUnitDto(staffUnit2);

        StaffUnit staffUnit3 = new StaffUnit();
        staffUnit3.setId(2L);
        staffUnit3.setName("unitName2");
        staffUnit3.setRoles(new HashSet<>());
        testReturnedNewStaffUnitDto = new StaffUnitDto(staffUnit3);


        // updateTest
        staffUnitModified = new StaffUnitDto(staffUnit3);
        staffUnitModified.setName(staffUnit3.getName() + " MODIFIED");
    }

    @Test
    @WithMockUser(username = "Bob", authorities = "ADMIN")
    void getAllStaffUnitNames() throws Exception {
        given(staffUnitService.getNames()).willReturn(unitNames);

        mvc.perform(get("/api/v1/staff_units/names")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", is("unitName1")));
    }

    @Test
    @WithMockUser(username = "Bob", authorities = "ADMIN")
    public void saveStaffUnitTest() throws Exception {
        given(staffUnitService.saveOrUpdate(testNewStaffUnitDto)).willReturn(testReturnedNewStaffUnitDto);
        writer = new StringWriter();
        mapper = new ObjectMapper();
        mapper.writeValue(writer, testNewStaffUnitDto);


        mvc.perform(post("/api/v1/staff_units/").content(writer.toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testReturnedNewStaffUnitDto.getId().intValue())))
                .andExpect(jsonPath("$.name", is(testReturnedNewStaffUnitDto.getName())));
    }

    @Test
    @WithMockUser(username = "Bob", authorities = "ADMIN")
    public void updateStaffUnitTest() throws Exception {
        given(staffUnitService.saveOrUpdate(staffUnitModified)).willReturn(staffUnitModified);
        writer = new StringWriter();
        mapper = new ObjectMapper();
        mapper.writeValue(writer, staffUnitModified);
          System.out.println(writer.toString());
        mvc.perform(put("/api/v1/staff_units/").content(writer.toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(staffUnitModified.getId().intValue())))
                .andExpect(jsonPath("$.name", is(staffUnitModified.getName())));
    }


    @Test
    @WithMockUser(username = "Bob", authorities = "ADMIN")
    public void deleteTest() throws Exception {
        Mockito.doNothing().when(staffUnitService).deleteById(testStaffUnitDto.getId());

        mvc.perform(delete("/api/v1/staff_units/" + testStaffUnitDto.getId()))
                .andExpect(status().isOk());
    }
}
