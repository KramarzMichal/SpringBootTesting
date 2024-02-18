package Spring.Boot.Testing.Spring_Boot_Testing.repository;

import Spring.Boot.Testing.Spring_Boot_Testing.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
