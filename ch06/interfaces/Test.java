/*
 * @Author: liubai
 * @Date: 2021-03-02
 * @LastEditTime: 2021-03-02
 */


import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        Employee[] staff= new Employee[3];

        staff[0]=new Employee("Harry",3);
        staff[1]=new Employee("Alice",1);
        staff[2]=new Employee("Boy",2);

        //sort
        Arrays.sort(staff);

        //print out info
        for(Employee e:staff){
            System.out.println("name:"+e.getName());
        }
        
    }    
}

