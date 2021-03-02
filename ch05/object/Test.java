/*
 * @Author: liubai
 * @Date: 2021-03-02
 * @LastEditTime: 2021-03-02
 */
package object;

public class Test {
    public static void main(String[] args) {
            Employee alice=new Employee("Alice", 5000);
            Employee a=alice;
            Employee alise=new Employee("Alise",5000,2010,12,12);
            
            //superClass equal test
            System.out.println("alice == a: " + (alice == a));
            System.out.println("alice.equals(a): " + alice.equals(a));
      
            System.out.println("alice == alise: " + (alice == alise));
            System.out.println("alice.equals(alise): " + alice.equals(alise));
            //toString
            System.out.println("alice.toString(): " + alice.toString());
            System.out.println("alice:"+alice);

            //hashcode
            System.out.println("hash code:"+alice.hashCode());

            Manager tonm=new Manager("Hari",5000);
            Manager t=tonm;
            Manager ton=new Manager("Hare",5000,2010,12,12);
            //subClass equal test
            System.out.println("tonm == t: " + (tonm == t));
            System.out.println("tonm.equals(t): " + tonm.equals(t));
      
            System.out.println("tonm == ton: " + (tonm == ton));
            System.out.println("tonm.equals(ton): " + tonm.equals(ton));
      
            
            System.out.println("tonm.toString(): " + tonm.toString());
            System.out.println("tonm:"+tonm);
            System.out.println("hash code:"+tonm.hashCode());
    }
}
