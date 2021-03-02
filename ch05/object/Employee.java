/*
 * @Author: liubai
 * @Date: 2021-03-02
 * @LastEditTime: 2021-03-02
 */
package object;

import java.time.*;
import java.util.Objects;

public class Employee {

    private String name;
    private double salary;
    private LocalDate hireDay;

    {
        hireDay=LocalDate.now();
    }

    //constructor 1
    public Employee(String name,double salary){
        this.name=name;
        this.salary=salary;
    }
    //constructor 2
    public Employee(String name,double salary,int year,int month,int day){
        this.name=name;
        this.salary=salary;
        this.hireDay=LocalDate.of(year, month, day);
    }

    //methods
    public double getSalary(){
        return salary;
    }
    public LocalDate getHireDay(){
        return hireDay;
    }

    //define equals  of superClass 
    public boolean equals(Object otherObject){
        // a quick test to see if the objects are identical
        if (this == otherObject) return true;
        
        // must return false if the explicit parameter is null
        if (otherObject == null) return false;

        // if the classes don't match, they can't be equal
        if (getClass() != otherObject.getClass()) return false;

        // now we know otherObject is a non-null Employee
        Employee other = (Employee) otherObject;

        // test whether the fields have identical values
        return Objects.equals(name, other.name) 
            && salary == other.salary 
            && Objects.equals(hireDay, other.hireDay);
    }

    //hashcode
    public int hashCode(){
      return Objects.hash(name, salary, hireDay); 
    }

    //object toString, System.out.print(Employee) will goto this method 
    public String toString(){
      return getClass().getName() 
        + "[name:" + name 
        + " salary:" + salary 
        + " hireDay:" + hireDay
        + "]";
    }
}

