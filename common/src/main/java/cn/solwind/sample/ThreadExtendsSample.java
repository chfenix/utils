package cn.solwind.sample;

public class ThreadExtendsSample extends Thread{

    private int intNo;

    public ThreadExtendsSample(int intNo) {
        this.intNo = intNo;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("ExtendsThread [" + intNo + "] is running! " + i);
                Thread.sleep(100);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
