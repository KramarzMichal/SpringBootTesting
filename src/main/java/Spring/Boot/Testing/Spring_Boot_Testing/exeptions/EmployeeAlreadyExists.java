package Spring.Boot.Testing.Spring_Boot_Testing.exeptions;

public class EmployeeAlreadyExists extends RuntimeException {
    public EmployeeAlreadyExists(String message) {
        super(message);
    }
}
