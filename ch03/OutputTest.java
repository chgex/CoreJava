import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

/*
 * @Author: liubai
 * @Date: 2021-02-25
 * @LastEditTime: 2021-02-25
 */
public class OutputTest {
    public static void main(String[] args) throws IOException {
        //读文件
        Scanner in = new Scanner(Paths.get("file.txt"),"UTF-8");
        
        //test
        String content=in.nextLine();
        int num=in.nextInt();
        System.out.println(content);
        
        //写文件
        PrintWriter out = new PrintWriter("outfile.txt","UTF-8");
        out.println(content);
        out.close();
    }    
}