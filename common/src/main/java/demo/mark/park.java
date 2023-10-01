package demo.mark;

import org.apache.velocity.runtime.directive.Break;

import java.util.Scanner;

public class park {

    public static void main(String[] args) {
        /*Scanner input = new Scanner(System.in);
        String val = null;       // 记录输入度的字符串
        do {
            System.out.print("请输入：");
            val = input.next();       // 等待输入值

            // 此处修改为实现的方法
            park(Integer.parseInt(val));

        } while (!val.equals("#"));   // 如果输入的值不版是#就继续输入
//        System.out.println("你输入了\"#\"，程序已经退出！");
        input.close(); // 关闭资源*/
        roundDay();
    }

    public static void roundDay(){

        for (int i = 0; i <4300 ; i+=60) {
            int money=park(i);
            if(money>=100){
                break;
            }
        }
    }
    public static int park(int min) {
        if (min <= 15) {
            //15分钟内免费
            System.out.println("免费停车");
            return 0;
        } else {
            //时间取整
            int hour = min / 60;

            //时间取余
            int mod = min % 60;


            //判断取余后是否大于零，大于零后加整进位

            if (mod > 0) {
                hour = hour + 1;
            }
            System.out.println("小时数"+hour);
            //除以24小时，能整除要多加一天费用，不能整除小于8小时按照实际算，大于8小时等于8小时
            int fakeHour = hour / 24 * 8;//整天转换的小时数
            int leftHour = hour % 24;//不整天小时数
            if (leftHour > 8) {
                leftHour = 8;
            }

            int price = 5;
            int money = fakeHour * price+leftHour*price;


            System.out.println("停车费：" + money);

        return money;
        }


    }
}

