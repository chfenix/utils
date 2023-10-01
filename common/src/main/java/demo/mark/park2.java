package demo.mark;

import org.apache.velocity.runtime.parser.node.ASTElseIfStatement;

public class park2 {
    public static void main(String[] args) {
    park(28*60);
    }
    public static void park(int min){
        if(min<=15){
            System.out.println("免费停车");
        }

        else{
            int hour=min/60;
            int mod=min%60;
            if(mod>0){

               hour=hour+1;
            }


            int zDay=hour/24;
            zDay=zDay*8;
            int bDay=hour%24;
            if(bDay>8){

                bDay=8;

            }
            int fakeHour = zDay + bDay;


            int price=5;
            int money=fakeHour*price;
            System.out.println("停车费："+ money);
        }




    }




}
