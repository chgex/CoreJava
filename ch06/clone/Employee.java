package clone; 

import java.util.Date;

/*
 * @Author: liubai
 * @Date: 2021-03-02
 * @LastEditTime: 2021-03-02
 */
class Employee implements Cloneable{
    private String name;
    private Date hireDay;

    //constructor
    public Employee(String name){
        this.name=name;
        hireDay=new Date();
    }

    //clone
    public Employee clone() throws CloneNotSupportedException
    {
        //call Object.clone()
        Employee cloned=(Employee) super.clone();

        //clone mutable fields
        cloned.hireDay=(Date)hireDay.clone();
        return cloned;
    }

    //print Employee
    public String toString(){
        return "Employee\n"+
        "[name:"+name +
        " hireDay:"+hireDay+
        "]";
    }


}