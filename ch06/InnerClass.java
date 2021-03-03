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



public class InnerClass {
    public static void main(String[] args) {
        TalkingClock clock=new TalkingClock(500, true);
        clock.start();

        //windows
        JOptionPane.showMessageDialog(null, "quit?");
        System.exit(0);
        
    }    
}

class TalkingClock
{
    private int interval;//时间间隔
    private boolean beep;

    //constructor
    public TalkingClock(int interval,boolean beep){
        this.interval=interval;
        this.beep=beep;
    }

    //method
    public void start(){
        //use inner calss:TimerPrinter
        ActionListener listener=new TimerPrinter();
        Timer t=new Timer(interval,listener);
        t.start();
    }

    //inner class
    public class TimerPrinter implements ActionListener
    {
        public void actionPerformed(ActionEvent event){
            System.out.println("time:"+new Date());
            if(beep){
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }
}
