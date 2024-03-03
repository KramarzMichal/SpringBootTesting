package Spring.Boot.Testing.Spring_Boot_Testing.service;

import Spring.Boot.Testing.Spring_Boot_Testing.entities.Employee;
import Spring.Boot.Testing.Spring_Boot_Testing.exeptions.EmployeeAlreadyExists;
import Spring.Boot.Testing.Spring_Boot_Testing.repository.EmployeeRepository;
import Spring.Boot.Testing.Spring_Boot_Testing.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class) // dodawać tylko jak korzystamy z adnotacji
class EmployeeServiceTest {
    //   test saveEmployee(Employee employee), który zwraca wyjątek
    //   test getAllEmployees() - pozytywny scenariusz
    //   test getAllEmployees() - negatywny scenariusz
    //   test getEmployeeById(long id);
    //   test updateEmployee(Employee updatedEmployee);
    //   test deleteEmployee(long id);
//    @Mock
//    private EmployeeRepository employeeRepository;
//    @InjectMocks
//    private EmployeeServiceImpl employeeService;
    private final EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    private final EmployeeServiceImpl employeeService = new EmployeeServiceImpl(employeeRepository);

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .firstName("Adam")
                .lastName("Małysz")
                .email("adam@gmail.com")
                .build();
    }

    @Test
    @DisplayName("Zapisywanie pracownika bez wyjątku")
    void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        // given
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        // when
        Employee savedEmployee = employeeService.save(employee);

        // then
        assertThat(savedEmployee).isNotNull();
//        assertThat(savedEmployee.getId()).isGreaterThan(0); // aby to odwzorować musimy mieć możliwość ustawiania ID
        assertThat(savedEmployee).isEqualTo(employee);
    }

    @Test
    @DisplayName("Zapisywanie pracownika, zwraca wyjątek")
    void givenEmployeeObject_whenSave_thenThrowException() {
        // given
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));
        // when
        // then
        assertThrows(EmployeeAlreadyExists.class, () -> employeeService.save(employee));
        verify(employeeRepository, never()).save(any(Employee.class)); // zobaczyć czy dany obiekt jest instacją implementacji interfejsu
    }

    @DisplayName("getAllEmployees() - pozytywny scenariusz")
    @Test
    void givenEmployeeList_whenGetAllEmployees_theReturnEmployeeList() {
        // given
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Andrzej")
                .lastName("Duda")
                .email("andrzej@duda.pl")
                .build();
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        // when
        List<Employee> employeeList = employeeService.findAll();

        // then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @DisplayName("getAllEmployees() - negatywny scenariusz")
    @Test
    void givenEmptyEmployeeList_whenGetAllEmployees_theReturnEmptyEmployeeList() {
        // given
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when
        List<Employee> employeeList = employeeService.findAll();

        // then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isEmpty();
    }

    @DisplayName("Test getEmployeeById(long id)")
    @Test
    void givenEmployeeId_whenFindById_thenReturnEmployee() {
        // given
        given(employeeRepository.findById(anyLong())).willReturn(Optional.of(employee));

        // when
        Optional<Employee> savedEmployee = employeeService.findById(employee.getId());

        // then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee).isEqualTo(Optional.of(employee));
    }

    @DisplayName("Test updateEmployee(Employee updatedEmployee)")
    @Test
    void givenEmployeeWithChangedFieldsWhenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("jarek@gimail.com");
        employee.setFirstName("Jarek");

        // when
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // then
        assertThat(updatedEmployee.getEmail()).isEqualTo("jarek@gimail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Jarek");
    }

    @DisplayName("Test deleteEmployee(long id)")
    @Test
    void givenEmployee_whenDelete_thenEmployeeNotPresent() {
        // given
        long employeeId = employee.getId();
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when
        employeeService.deleteById(employeeId);

        // then
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

}