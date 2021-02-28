import java.time.LocalDate;
import java.util.Random;


/*
 * @Author: liubai
 * @Date: 2021-02-28
 * @LastEditTime: 2021-02-28
 */
public class EmployeeTest {
    public static void main(String[] args) {
        // fill the staff array with three Employee objects
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Harry", 40000,2020,12,15);
        staff[1] = new Employee("Carl",40000,2021,10,10);
        staff[2] = new Employee("Tony",40000,2021,6,15);

        //raise salary
        for(Employee e:staff){
            e.raiseSalary(5);
        }

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
    private LocalDate hireDay;
    
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
    public Employee(String aName,double aSalary,int year,int month,int day){
        name=aName;
        salary=aSalary;
        hireDay=LocalDate.of(year,month,day);
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
    public LocalDate getHireDay(){
        return hireDay;
    }
    public void raiseSalary(double percent){
        double raise=salary*percent/100;
        salary+=raise;
    }
}