public class Employee extends Person{
    private double salary;

    public Employee(String name, String surname, double salary) {
        super(name, surname);
        this.salary = salary;
    }

    public Employee(double salary) {
        this.salary = salary;
    }

    public Employee(){};

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return super.toString() + " " + salary + " zł";
    }
}
