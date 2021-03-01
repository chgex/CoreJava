package inheritance;

/*
 * @Author: liubai
 * @Date: 2021-03-01
 * @LastEditTime: 2021-03-01
 */
public class Manager extends Employee
{
    private double bonus;
    public Manager(String name,double salary){
        super(name, salary);
        bonus=0;
    }
    public double getSalary(){
        double baseSalary = super.getSalary();
        return baseSalary + bonus;
    }
    public void setBonus(double b){
      bonus = b;
    }
}