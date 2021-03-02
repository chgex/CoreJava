/*
 * @Author: liubai
 * @Date: 2021-03-02
 * @LastEditTime: 2021-03-02
 */
package abstractClass;

import java.time.LocalDate;

public class Employee extends Person{
    //name,salary,(hireDay)
    private double salary;
    private LocalDate hireDay;

    {
        hireDay=LocalDate.now();
    }
    //constructor 1
    public Employee(String name,double salary){
        super(name);
        this.salary=salary;
    }
    //constructor 2
    public Employee(String name,double salary,int year,int month,int day){
        super(name);
        this.salary=salary;
        this.hireDay=LocalDate.of(year, month, day);
    }
    
    public double getSalary(){
        return salary;
    }
    public LocalDate getHireDay(){
        return hireDay;
    }
    
    //overloading
    public String getDescription(){
        return String.format("an employee with a salary of :$%.2f,hireDay:%s", salary,getHireDay().toString());
    }
}

