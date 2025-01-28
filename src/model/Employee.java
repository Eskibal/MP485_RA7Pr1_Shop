/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author jeanp
 */
public class Employee extends Person implements main.Logable{
    private int employeeID;
    private String password;
    
    public static final int EMPLOYEE_ID = 123;
    public static final String PASSWORD = "test";

    public Employee(String name) {
        super(name);
        this.employeeID = EMPLOYEE_ID;
        this.password = PASSWORD;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean login (int user, String password) {
        return this.employeeID == user && this.password.equals(password);
    }
    
}
