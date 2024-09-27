import java.io.Serializable;

public class Company implements Serializable {
    private static final int MAX_EMPLOYEES = 3;
    private Employee[] employees = new Employee[3];

    public Company(Employee[] employees) {
        this.employees = employees;
    }

    public Company() {
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public void setEmployees(Employee[] employees) {
        this.employees = employees;
    }
}
