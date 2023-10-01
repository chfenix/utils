package demo.mark;

import java.util.Scanner;

public class money {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String val = null;       // 记录输入度的字符串
        do{
            System.out.print("请输入：");
            val = input.next();       // 等待输入值

            // 此处修改为实现的方法
            buysou(Integer.parseInt(val));

        }while(!val.equals("#"));   // 如果输入的值不版是#就继续输入
//        System.out.println("你输入了\"#\"，程序已经退出！");
        input.close(); // 关闭资源
    }

    public static void buysou(int money) {
        System.out.println("输入的金额是:" + money);

        if(money<1000){

            System.out.println("买个上网本");
        }
        else if(money>=1000&&money<5000){
            System.out.println("买个笔记本");
        }
        else if(money>=5000&&money<10000){
            System.out.println("买个台式机");
        }
        else if(money>=10000&&money<100000){
            System.out.println("买个服务器");
        }
        else {

            System.out.println("想买啥都行");
        }
        }

    }





