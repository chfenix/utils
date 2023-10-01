package demo.mark;

public class park1 {
    public static void main(String[] args) {
    }

    public static void park(int min) {

        if (min <= 15) {
            System.out.println("免费停车");
        } else {
            //如果大于15分钟，小于60分钟 收费
            //如果大于60分钟小于480分钟按时实际收费
            //如果大于480分钟等于8小时收费
            int hour = min / 60;
            int mod = min % 60;
            if (hour > 8) {
                hour = hour + 1;
            } else {
                int par = 5;
                int money = hour * par;
                System.out.println("停车费：" + money);
            }

        }
    }
}

