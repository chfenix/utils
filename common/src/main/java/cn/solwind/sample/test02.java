package cn.solwind.sample;

public class test02 {
    public static void main(String[] args) {

        int money=200000;
        double rate=0.05;
        int days=365;
        double result=money*rate/days;
        int money1=200000;
        double rate1=0.04;
        double result1=money1*rate1/days;
        System.out.println(result);
        System.out.println(result1);

        double mode=result-result1;


        System.out.println(mode);




    }
}
