package Spring.Boot.Testing.Spring_Boot_Testing.service;

import Spring.Boot.Testing.Spring_Boot_Testing.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee save(Employee employee);
    List<Employee> findAll();
    Optional<Employee> findById(long id);
    Employee updateEmployee(Employee updatedEmployee);
    Optional<Employee> updateEmployee (long id, Employee updatedEmployee);
    void deleteById(long id);
}
