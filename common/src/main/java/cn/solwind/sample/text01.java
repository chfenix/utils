public class text01 {

    public static void main(String[] args) {

        //
        int peoplecount = 0;
        int busstopcount = 0;
        double toultmoney = 0.0;
        peoplecount += 1;
        busstopcount = busstopcount + 1;
        toultmoney = toultmoney + 0.8;
        //
        peoplecount = peoplecount + 3;
        peoplecount = peoplecount - 1;
        busstopcount = busstopcount + 1;
        toultmoney = toultmoney + 0.8*3;

        //
        peoplecount = peoplecount + 2;
        peoplecount = peoplecount - 2;
        busstopcount = busstopcount + 1;
        toultmoney = toultmoney + 0.8;
        toultmoney = toultmoney + 0.8;
        //
        busstopcount = busstopcount + 1;
        //
        peoplecount = peoplecount + 2;
        peoplecount = peoplecount - 1;
        busstopcount = busstopcount + 1;
        toultmoney = toultmoney + 0.8;
        toultmoney = toultmoney + 0.4;
        //
        busstopcount = busstopcount + 1;
        //
        peoplecount = peoplecount + 1;
        busstopcount = busstopcount + 1;
        toultmoney = toultmoney + 0.0;
        String title="ecoun";
        System.out.println(title+":"+peoplecount+" busstopcount:"+busstopcount +" toultmoney:"+toultmoney);
    }
}


