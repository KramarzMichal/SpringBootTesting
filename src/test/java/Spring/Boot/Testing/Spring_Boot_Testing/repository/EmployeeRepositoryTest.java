package Spring.Boot.Testing.Spring_Boot_Testing.repository;

import Spring.Boot.Testing.Spring_Boot_Testing.entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class EmployeeRepositoryTest {
    // TDD - test driven development - najpierw piszemy test a potem metody
    // BDD - behavior driven development- dzielenie na sekcje:
    // given - aktualne wartości, obiekty
    // when - akcja lub testowane zachowanie
    // then - weryfikacja wyniku
    @Autowired
    private EmployeeRepository employeeRepository; //w testach wstrzykujemy przez pole

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .firstName("Jan")
                .lastName("Nowak")
                .email("jnowak@gmail.com")
                .build();
    }

    @Test
    @DisplayName("Zapisywanie pracownika")
    void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        // given
        // when
        Employee savedEmployee = employeeRepository.save(employee);

        // then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
        assertThat(savedEmployee).isEqualTo(employee);
    }

    @DisplayName("pobieranie wszystkich pracowników")
    @Test
    void givenEmployeeList_whenFindAll_thenReturnEmployeeList() {
        // given
        Employee employee2 = Employee.builder()
                .firstName("Adam")
                .lastName("Małysz")
                .email("adam@gmail.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        // when
        List<Employee> employeeList = employeeRepository.findAll();

        // then
        assertThat(employeeList).isNotNull();
        assertThat((employeeList).size()).isEqualTo(2);
    }

    @DisplayName("Pobieranie pracownika po Id")
    @Test
    void shouldFindEmployeeById() {
        //given
        employeeRepository.save(employee);
        //when
        Employee employeeById = employeeRepository.findById(employee.getId()).get();
        //then
        assertThat(employeeById).isNotNull();
        assertThat(employeeById).isEqualTo(employee);
    }

    @DisplayName("Pobieranie po adresie email")
    @Test
    void shouldFindEmplayeeByEmail() {
        //given
        employeeRepository.save(employee);
        //when
        Employee employeeByEmail = employeeRepository.findByEmail(employee.getEmail()).get();
        //then
        assertThat(employeeByEmail).isNotNull();
        assertThat(employeeByEmail).isEqualTo(employee);
    }

    @DisplayName("Uaktualnienie danych pracownika")
    @Test
    void shouldUpdateEmployeeData() {
        //given
        employeeRepository.save(employee);
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("adrzej@duda.pl");
        savedEmployee.setFirstName("Andrzej");
        //when
        Employee updatedEmployee = employeeRepository.save(savedEmployee);
        //then
        assertThat(savedEmployee.getEmail()).isEqualTo(updatedEmployee.getEmail());
        assertThat(savedEmployee.getFirstName()).isEqualTo((updatedEmployee.getFirstName()));
    }

    @DisplayName("Usuwanie praconika")
    @Test
    void shouldDeleteEmployee() {
        //given
        employeeRepository.save(employee);
        //when
        employeeRepository.deleteById(employee.getId());
        //then
        assertThat(employeeRepository.findById(employee.getId()).isEmpty());
    }

    @DisplayName("Testy dla zapytań z indeksowanymi parametrami")
    @Test
    void givenEmployee_whenFindByFirstNameAndLastName_thenReturnEmployee() {
        //given
        Employee savedEmployee = employeeRepository.save(employee);
        //when
        Employee foundEmployee = employeeRepository.findByFirstNameAndLastName(savedEmployee.getFirstName(), savedEmployee.getLastName()).get();
        //then
        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee).isEqualTo(savedEmployee);
    }

}
// pobieranie pracowników po id
// pobieranie pracowników po email
// uaktualnianie danych pracowników
// usuwanie pracownika
// testy dla zapytań JPQL
// testy dla zapytań z indeksowanymi parametrami
// testy dla zapytań z nazwami parametrów
