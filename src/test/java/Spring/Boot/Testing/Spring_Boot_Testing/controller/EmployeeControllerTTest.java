package Spring.Boot.Testing.Spring_Boot_Testing.controller;

import Spring.Boot.Testing.Spring_Boot_Testing.entities.Employee;
import Spring.Boot.Testing.Spring_Boot_Testing.repository.EmployeeRepository;
import Spring.Boot.Testing.Spring_Boot_Testing.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EmployeeControllerITTest {
    @Autowired
    private MockMvc mockMvc; // niezbędne do testów MVC - aby można było wywoływać endpointy

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper; // do deserializacji JSON na obiekt oraz serializacji

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("test tworzenia pracownika")
    void givenEmployee_whenCreatedEmployee_thenReturnSavedEmployee() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("Adam")
                .lastName("Małysz")
                .email("adam@gmail.com")
                .build();

//        given(employeeService.save(any(Employee.class)))
//                .willAnswer(invocation -> invocation.getArgument(0)); // ustawiamy '0' ponieważ 'save' ma tylko jeden parametr o indeksie '0'
        // when
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    @DisplayName("Test pobierania pracowników")
    @Test
    void givenListOfEmployees_whenFindAll_thenReturnEmployeeList() throws Exception {
        // given
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("Adam").lastName("Małysz").email("adam@gmail.com").build());
        listOfEmployees.add(Employee.builder().firstName("Marek").lastName("Zegarek").email("jarek@gmail.com").build());
        employeeRepository.saveAll(listOfEmployees);

        // when
        ResultActions response = mockMvc.perform(get("/api/employees"));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(listOfEmployees.size())));
    }

    @DisplayName("Pobierania pracownika po id (pozytywny scenariusz)")
    @Test
    void givenEmployeeId_whenFindById_thenReturnEmployee() throws Exception {
        // given
        Employee employee = Employee.builder().firstName("Adam").lastName("Małysz").email("adam@gmail.com").build();
        employeeRepository.save(employee);

        // when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andDo(print())
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    @DisplayName("Pobierania pracownika po id (negatywny scenariusz)")
    @Test
    void givenInvalidEmployeeId_whenFindById_thenReturnNotFound() throws Exception {
        // given
        // when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", 1L));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("Uaktualniane pracownika (pozytywny scenariusz)")
    @Test
    void givenEmployeeId_whenUpdated_thenReturnEmployee() throws Exception {
        // given
        Employee employee = Employee.builder().firstName("Adam").lastName("Małysz").email("adam@gmail.com").build();
        employeeRepository.save(employee);


        // when
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    @DisplayName("Uaktualniane pracownika (pozytywny negatywny)")
    @Test
    void givenInvalidEmployeeId_whenUpdated_thenReturnEmployee() throws Exception {
        // given
        Employee employee = Employee.builder().firstName("Adam").lastName("Małysz").email("adam@gmail.com").build();
        long invalidEmployeeId = 999999L;

        // when
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", invalidEmployeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then
        response.andDo(print())
                .andExpect(status().isNotFound());

    }

    @DisplayName("Usuwania pracownika")
    @Test
    void givenEmployeeId_whenDeleted_thenReturn200() throws Exception {
        // given
        Employee employee = Employee.builder().firstName("Adam").lastName("Małysz").email("adam@gmail.com").build();
        employeeRepository.save(employee);

        // when
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employee.getId()));

        // then
        response.andDo(print())
                .andExpect(status().isOk());
    }

}
