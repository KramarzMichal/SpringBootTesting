package Spring.Boot.Testing.Spring_Boot_Testing.repository;

import Spring.Boot.Testing.Spring_Boot_Testing.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // JPQL - Java Persistence Query Language
    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    // To samo co powyżej za pomocą JPQL z użyciem własnego zapytania
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Optional<Employee> findByJPQL(String firstName, String lastName);

    @Query(value = "select * from employees e where e.first_name = ?1 and e.last_name = ?2", nativeQuery = true)
    Optional<Employee> findByNativeSQL(String firstName, String lastName);

    @Query(value = "select * from employees e where e.first_name = :xyz and e.last_name = :lastName"
            , nativeQuery = true)
    Optional<Employee> findByNativeSqlNamedParams(@Param("xyz") String firstName, String lastName);
}
