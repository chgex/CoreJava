import java.util.Random;

/*
 * @Author: liubai
 * @Date: 2021-02-28
 * @LastEditTime: 2021-02-28
 */
public class ConstructorTest {
    public static void main(String[] args) {
        // fill the staff array with three Employee objects
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Harry", 40000);
        staff[1] = new Employee(60000);
        staff[2] = new Employee();

        // print out information about all Employee objects
        for (Employee e : staff)
            System.out.println("name:" + e.getName()
                + ",id:" + e.getId() 
                + ",salary:"+ e.getSalary());
    }    
}

class Employee
{
    //静态域
    private static int nextId=100;

    //数据域
    private int id;
    private String name="";//初始化实例域
    private double salary;
    
    //静态初始化块    
    // {
    //    Random generator=new Random();
    //    nextId=generator.nextInt(100);
    // }
    //init id
    {
        id=nextId;
        nextId++;
    }

    //3 overloading constructor
    public Employee(String aName,double aSalary){
        name=aName;
        salary=aSalary;
    }
    public Employee(double aSalary){
        //call other constructor
        this("#"+nextId,aSalary);
    }
    //default constructor
    public Employee(){
    
    }

    //methods
    public String getName(){
        return name;
    }
    public double getSalary(){
        return salary;
    }
    public int getId(){
        return id;
    }
}