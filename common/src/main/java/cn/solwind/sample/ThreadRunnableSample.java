package cn.solwind.sample;

public class ThreadRunnableSample implements Runnable{
    private int intNo;

    public ThreadRunnableSample(int intNo) {
        this.intNo = intNo;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("RunnableThread [" + intNo + "] is running! " + i);
                Thread.sleep(100);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
