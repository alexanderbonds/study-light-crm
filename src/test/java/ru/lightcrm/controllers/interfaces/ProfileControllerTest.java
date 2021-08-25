package ru.lightcrm.controllers.interfaces;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.lightcrm.entities.Department;
import ru.lightcrm.entities.StaffUnit;
import ru.lightcrm.entities.dtos.ProfileDto;
import ru.lightcrm.entities.dtos.ProfileFullDto;
import ru.lightcrm.entities.dtos.SystemUserDto;
import ru.lightcrm.services.interfaces.DepartmentService;
import ru.lightcrm.services.interfaces.ProfileService;
import ru.lightcrm.services.interfaces.StaffUnitService;
import ru.lightcrm.services.interfaces.UserService;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProfileControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProfileService profileService;
    @MockBean
    private UserService userService;
    @MockBean
    private StaffUnitService staffUnitService;
    @MockBean
    private DepartmentService departmentService;

    private static String firstname;
    private static String lastname;
    private static String middlename;
    private static String staffUnitName;
    private static String departmentName;
    private static List<ProfileDto> testProfileDtoList;
    private static List<ProfileFullDto> testProfileFullDtoList;
    private static ProfileDto testProfileDto;
    private static ProfileFullDto testProfileFullDto;
    private static SystemUserDto testSystemUserDto;
    private static StaffUnit testStaffUnit;
    private static Department testDepartment;

    @BeforeAll
    public static void prepareTestData() {
        firstname = "Тест";
        lastname = "Тестов";
        middlename = "Тестович";
        staffUnitName = "Тестовая должность";
        departmentName = "Тестовый отдел";
        testProfileDto = new ProfileDto();
        testProfileDto.setId(1L);
        testProfileDto.setFirstname(firstname);
        testProfileDto.setLastname(lastname);
        testProfileDto.setMiddlename(middlename);
        testProfileDtoList = List.of(testProfileDto);
        // Full
        testProfileFullDto = new ProfileFullDto();
        testProfileFullDto.setSex("M");
        testProfileFullDto.setPhone("80001112233");
        testProfileFullDto.setEmail("test@email.com");
        testProfileFullDtoList = List.of(testProfileFullDto);
        // SystemUserDto
        testSystemUserDto = new SystemUserDto();
        testSystemUserDto.setLogin("Aladdin");
        testSystemUserDto.setPassword("12345");
        testSystemUserDto.setConfirmationPassword("12345");
        testSystemUserDto.setFirstname(firstname);
        testSystemUserDto.setLastname(lastname);
        testSystemUserDto.setMiddlename(middlename);
        testSystemUserDto.setStaffUnitName(staffUnitName);
        testSystemUserDto.setEmploymentDate(OffsetDateTime.now());
        testSystemUserDto.setDepartmentNames(Collections.singletonList(departmentName));
        // StaffUnit
        testStaffUnit = new StaffUnit();
        testStaffUnit.setName(staffUnitName);
        // Department
        testDepartment = new Department();
        testStaffUnit.setName(departmentName);
    }

    @Test
    @WithMockUser(username = "Bob", authorities = "ADMIN")
    public void getAllProfilesDtoTest() throws Exception {
        given(profileService.findDtoAll()).willReturn(testProfileDtoList);

        mvc.perform(get("/api/v1/profiles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstname", is(testProfileDtoList.get(0).getFirstname())))
                .andExpect(jsonPath("$[0].lastname", is(testProfileDtoList.get(0).getLastname())))
                .andExpect(jsonPath("$[0].middlename", is(testProfileDtoList.get(0).getMiddlename())));
    }

    @Test
    @WithMockUser(username = "Bob", authorities = "ADMIN")
    public void getAllProfilesFullDtoTest() throws Exception {
        given(profileService.findFullDtoAll()).willReturn(testProfileFullDtoList);

        mvc.perform(get("/api/v1/profiles/full")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].sex", is(testProfileFullDtoList.get(0).getSex())))
                .andExpect(jsonPath("$[0].phone", is(testProfileFullDtoList.get(0).getPhone())))
                .andExpect(jsonPath("$[0].email", is(testProfileFullDtoList.get(0).getEmail())));
    }

    @Test
    @WithMockUser(username = "Bob", authorities = "ADMIN")
    public void getProfileTest() throws Exception {
        given(profileService.findDtoById(1L)).willReturn(testProfileDto);

        mvc.perform(get("/api/v1/profiles/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is(testProfileDto.getFirstname())))
                .andExpect(jsonPath("$.lastname", is(testProfileDto.getLastname())))
                .andExpect(jsonPath("$.middlename", is(testProfileDto.getMiddlename())));
    }

    @Test
    @WithMockUser(username = "Bob", authorities = "ADMIN")
    public void getProfileFullTest() throws Exception {
        given(profileService.findFullDtoById(1L)).willReturn(testProfileFullDto);

        mvc.perform(get("/api/v1/profiles/full/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sex", is(testProfileFullDto.getSex())))
                .andExpect(jsonPath("$.phone", is(testProfileFullDto.getPhone())))
                .andExpect(jsonPath("$.email", is(testProfileFullDto.getEmail())));
    }

    @Test
    @WithMockUser(username = "Bob", authorities = "ADMIN")
    public void saveNewUserTest() throws Exception {
        given(staffUnitService.findByName(staffUnitName)).willReturn(testStaffUnit);
        given(departmentService.findOneByName(departmentName)).willReturn(testDepartment);

        String json = "{\"firstname\":\"Иван\",\"lastname\":\"Иванов\",\"middlename\":\"Иванович\"," +
                "\"staffUnitName\":\"Администратор\",\"employmentDate\":\"2021-03-22\"," +
                "\"departmentNames\":[\"IT-отдел\"],\"login\":\"Aladdin\",\"password\":\"12345\"," +
                "\"confirmationPassword\":\"12345\"}";

        mvc.perform(post("/api/v1/profiles/register").content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
