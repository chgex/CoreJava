import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
/*
 * @Author: liubai
 * @Date: 2021-03-03
 * @LastEditTime: 2021-03-03
 */
public class LambdaTest {
    public static void main(String[] args) {
        String[] names=new String[]{"alice","alic","boy","cual","harry"};

        //System.out.println(names.toString());//equal to System.out.println(names);
        //[Ljava.lang.String;@279f2327
        
        //System.out.println(Arrays.toString(names));
        //[alice, alic, boy, cual, harry]
        
        //sorted by default
        Arrays.sort(names);
        System.out.println("sorted by default:");
        System.out.println(Arrays.toString(names));

        //sorted by length,use lambda
        Arrays.sort(names,(first,second)->{
            return first.length()-second.length();
        });
        System.out.println("sorted by length:");
        System.out.println(Arrays.toString(names));
        
        //timer, use lambda
        Timer t=new Timer(500,event->{
            System.out.println("time:"+new Date());
        });
        t.start();

        //window
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);   
    }
    
}
