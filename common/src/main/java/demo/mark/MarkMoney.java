package demo.mark;

public class MarkMoney {
    public static void main(String[] args) {

        double money = 5;
        double apple = 3.99;

        double result=markChane(money,apple);
        System.out.println("客户找零："+result);


    }
    public static double markChane(double money,double item){

        return money-item;


    }




}



