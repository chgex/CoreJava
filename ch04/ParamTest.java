import java.time.LocalDate;
import java.util.Random;


/*
 * @Author: liubai
 * @Date: 2021-02-28
 * @LastEditTime: 2021-02-28
 */
public class ParamTest {
    public static void main(String[] args) {
        
        Employee a = new Employee("Harry", 40000,2020,12,15);
        Employee b = new Employee("Carl",40000,2021,10,10);
        System.out.println("a:"+a.getName());
        System.out.println("b:"+b.getName());
        
        Employee.swap(a,b);
        System.out.println("end of swap");
        System.out.println("a:"+a.getName());
        System.out.println("b:"+b.getName());
        
        
        int x=10;
        System.out.println("x:"+x);
        Employee.tripeValue(x);
        System.out.println("Employee.tripeValue(x)");
        System.out.println("x:"+x);

        System.out.println("a.getSalary:"+a.getSalary());
        Employee.tripeSalary(a);
        System.out.println("Employee.tripeSalary(a)");
        System.out.println("a.getSalary:"+a.getSalary());
        
        //print info of a
        System.out.println("print info of a");
        System.out.println("a.getName:"+a.getName()
            +"  a.getId():"+a.getId()
            +"  a.hireday:"+a.getHireDay());
        
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

    //paraments test
    public static void tripeValue(double x){ //doesn't work
        x=x*3;
    }
    public static void tripeSalary(Employee x){ //work
        x.raiseSalary(200);
        //System.out.println("salary:"+x.getSalary());
    }
    //swap
    public static void swap(Employee a,Employee b){ //dosen't work
        Employee tmp=a;
        a=b;
        b=tmp;
        System.out.println("end of a:"+a.getName());
        System.out.println("end of b:"+b.getName());
    }

}