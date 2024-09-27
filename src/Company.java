import java.io.Serializable;

public class Company implements Serializable {
    public static final int MAX_EMPLOYEES = 3;
    private Employee[] employees = new Employee[MAX_EMPLOYEES];
    private int index = 0;

    public Company(Employee[] employees) {
        this.employees = employees;
    }

    public Company() {
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee){
        employees[index] = employee;
        index++;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Employee employee : employees) {
            if(employee!=null){
                builder.append(employee.toString());
                builder.append("\n");
            }

        }
        return builder.toString();
    }
}
