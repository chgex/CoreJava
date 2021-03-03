/*
 * @Author: liubai
 * @Date: 2021-03-03
 * @LastEditTime: 2021-03-03
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;



public class AnnoyInnerClass {
    public static void main(String[] args) {
        TalkingClock3 clock=new TalkingClock3();
        clock.start(500,true);

        //windows
        JOptionPane.showMessageDialog(null, "quit?");
        System.exit(0);
        
    }    
}

//don't use public
class TalkingClock3
{
    // private int interval;//时间间隔
    // private boolean beep;

    //constructor will noe need
    //because method start's  will this 2 params
    // public TalkingClock2(int interval,boolean beep){
    //     this.interval=interval;
    //     this.beep=beep;
    // }

    //method, use local class
    public void start(int interval,boolean beep){
        //local calss:TimerPrinter
        //dont's use public or private
        // class TimerPrinter implements ActionListener
        // {
        //     public void actionPerformed(ActionEvent event){
        //         System.out.println("time:"+new Date());
        //         if(beep)
        //             Toolkit.getDefaultToolkit().beep();
        //     }
        // }
        
        //anony Inner Class
        ActionListener listener=new ActionListener()
        {
            public void actionPerformed(ActionEvent event){
                System.out.println("time:"+new Date());
                if(beep)
                    Toolkit.getDefaultToolkit().beep();
            }
        };
        Timer t=new Timer(interval,listener);
        t.start();
    }
}
