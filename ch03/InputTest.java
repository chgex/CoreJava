import java.util.Scanner;

/*
 * @Author: liubai
 * @Date: 2021-02-25
 * @LastEditTime: 2021-02-25
 */
public class InputTest {
    public static void main(String[] args) {
        //1.scanner
        Scanner in=new Scanner(System.in);
        
        //2.input
        System.out.print("inpout your name:");
        String name=in.nextLine();

        System.out.print("input your age:");
        int age=in.nextInt();
        
        //3.display
        System.out.println("Hello " + name + ",next year, you'll be " + (age+1));
        //使用静态方法，创建一个格式化字符串
        String msg=String.format("hello %s,next year, you'll be %d", name,age+1);
        System.out.println(msg);
    }    
}
