/*
 * @Author: liubai
 * @Date: 2021-02-25
 * @LastEditTime: 2021-02-25
 */
public class StringArgs {
    public static void main(String[] args) {
        if(args.length==0){
            System.out.println("there has 0 args");
        }else if(args[0].equals("h")){
            System.out.println("hello");
        }
        for(int i=0;i<args.length;i++){
            System.out.printf("the %d arg is %s \n",i,args[i]);
        }
    }   
}