package demo.mark;

import java.util.Scanner;

public class sample {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String val = null;       // 记录输入度的字符串
        do{
            System.out.print("请输入：");
            val = input.next();       // 等待输入值

            // 此处修改为实现的方法
            test(val);

        }while(!val.equals("#"));   // 如果输入的值不版是#就继续输入
//        System.out.println("你输入了\"#\"，程序已经退出！");
        input.close(); // 关闭资源
    }


    public  static  boolean test(String val) {
        return false;
    }



}
