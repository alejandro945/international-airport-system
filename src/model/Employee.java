package model;

public class Employee extends Person {
    private int salary;

    public Employee(String name, String lastName, long id, int salary) {
        super(name, lastName, id);
        this.salary = salary;
    }

    /**
     * @return int
     */
    public int getSalary() {
        return this.salary;
    }

    /**
     * @param salary
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

}
