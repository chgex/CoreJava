/*
 * @Author: liubai
 * @Date: 2021-03-02
 * @LastEditTime: 2021-03-02
 */
package abstractClass;

public class Student extends Person{

    private String major;

    {
        major="computer science";//default
    }
    
    //constructor 1
    public Student(String name,String major){
        super(name);
        this.major=major;
    }
    //constructor 2
    public Student(String name){
        super(name);
    }

    //overloading
    public String getDescription(){
        return "a student major in "+major;
    }
    
}

