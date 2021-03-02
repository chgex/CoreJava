/*
 * @Author: liubai
 * @Date: 2021-03-02
 * @LastEditTime: 2021-03-02
 */

public class Employee implements Comparable<Employee>{
    
    private String name;
    private double level;
    
    public Employee(String name,double level){
        this.name=name;
        this.level=level;
    }
    
    //methods ...
    public String getName(){
        return name;
    }

    //overwrite compare
    public int compareTo(Employee other){
        return Double.compare(level,other.level);
    }
}

