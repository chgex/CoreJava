/*
 * @Author: liubai
 * @Date: 2021-03-02
 * @LastEditTime: 2021-03-02
 */
package abstractClass;

public class Test {
    public static void main(String[] args) {
        Person[] people=new Person[4];

        people[0]=new Employee("Harry", 1000);
        people[1]=new Employee("Caul", 1000,2010,12,12);

        people[2]=new Student("tonm");
        people[3]=new Student("jire","cat and rat");

        for(Person p:people){
            System.out.println(p.getName());
            System.out.println(p.getDescription());
        }
    }
}