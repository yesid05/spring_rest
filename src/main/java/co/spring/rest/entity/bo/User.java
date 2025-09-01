package co.spring.rest.entity.bo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class User{
    private int id;
    private String name;
    private String lastName;
    private LocalDate birthDay;
    private BigDecimal salary;
    private boolean active;

    public User(){}
    
    public User(int id, String name, String lastName, LocalDate birthDay, BigDecimal salary, boolean active) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.salary = salary;
        this.active = active;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public LocalDate getBirthDay() {
        return birthDay;
    }
    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }
    public BigDecimal getSalary() {
        return salary;
    }
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", lastName=" + lastName + ", birthDay=" + birthDay + ", salary="
                + salary + ", active=" + active + "]";
    }
}
