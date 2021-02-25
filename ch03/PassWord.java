import java.io.Console;

/*
 * @Author: liubai
 * @Date: 2021-02-25
 * @LastEditTime: 2021-02-25
 */

public class PassWord {
    public static void main(String[] args) {
        Console cons=System.console();

        String username=cons.readLine("user name:");
        //返回的密码放在一维数组中
        char[] pwd=cons.readPassword("password:");

        System.out.println("user:"+username);
        System.out.println("password:"+pwd);
    }   
}