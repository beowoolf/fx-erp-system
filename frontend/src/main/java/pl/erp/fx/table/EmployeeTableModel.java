package pl.erp.fx.table;

import javafx.beans.property.SimpleStringProperty;

public class EmployeeTableModel {

    private final Long idEmployee;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty salary;

    public EmployeeTableModel(Long idEmployee, String firstName, String lastName, String salary) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.salary = new SimpleStringProperty(salary);
        this.idEmployee = idEmployee;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public String getSalary() {
        return salary.get();
    }

    public void setSalary(String salary) {
        this.salary.set(salary);
    }

    public SimpleStringProperty salaryProperty() {
        return salary;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

}
