package inheritance;
/*
 * @Author: liubai
 * @Date: 2021-03-01
 * @LastEditTime: 2021-03-02
 */

public class Employee {

    private String name;
    private double salary;

    public Employee(String name,double salary){
        this.name=name;
        this.salary=salary;
    }
    public String getName(){
        return name;
    }
    public double getSalary(){
        return salary;
    }
}