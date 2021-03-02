package inheritance;

/*
 * @Author: liubai
 * @Date: 2021-03-01
 * @LastEditTime: 2021-03-02
 */
public class ManagerTest {
    public static void main(String[] args) {
        Manager boss=new Manager("carl", 5000);
        boss.setBonus(5000);

        Employee[] staff = new Employee[3];
        staff[0] = boss;
        staff[1] = new Employee("Harry", 5000);
        staff[2] = new Employee("Tommy", 5000);

        // print out information about all Employee objects
        for (Employee e : staff)
            System.out.println("name:" + e.getName() 
            + ",salary:" + e.getSalary());
    }
}