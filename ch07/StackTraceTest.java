import java.util.Scanner;

/*
 * @Author: liubai
 * @Date: 2021-03-04
 * @LastEditTime: 2021-03-04
 */
public class StackTraceTest
{
    public static int factor(int n){
        System.out.println("factor:"+n);
        Throwable t=new Throwable();
        StackTraceElement[] frames=t.getStackTrace();
        for(StackTraceElement f:frames)
            System.out.println(f);
        int r;
        if(n<=1)
            r=1;
        else
            r=n*factor(n-1); 
        return r;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n=in.nextInt();
        in.close();
        factor(n);
    }
} 