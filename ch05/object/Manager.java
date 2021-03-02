/*
 * @Author: liubai
 * @Date: 2021-03-02
 * @LastEditTime: 2021-03-02
 */
package object;


public class Manager extends Employee{

    private double bonus;

    //constructor 1
    public Manager(String name,double salary,int year,int month,int day){
        super(name,salary,year,month,day);
        this.bonus=1000;
    }
    //constructor 2
    public Manager(String name,double salary){
        super(name,salary);
    }

    //method
    public void setBonus(double bonus){
        this.bonus=bonus;
    }
    //overloading
    public double getSalary(){
      double baseSalary = super.getSalary();
      return baseSalary + bonus;
    }
    /*//define equals  of subClass */
    public boolean equals(Object otherObject){
        if (!super.equals(otherObject)) return false;
        Manager other = (Manager) otherObject;
        
        // super.equals checked that this and other belong to the same class
        return bonus == other.bonus;
    }
    //hash code
    public int hashCode(){
      return java.util.Objects.hash(super.hashCode(), bonus);
    }
    //toString
    public String toString(){
      return super.toString() 
        + "[bonus:" + bonus 
        + "]";
   }

}
