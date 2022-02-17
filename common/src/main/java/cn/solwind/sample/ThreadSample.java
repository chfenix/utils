package cn.solwind.sample;

public class ThreadSample {

    public static void main(String[] args) {
        /**
         * 继承Thread
         */
        for (int i = 0; i < 10; i++) {
            ThreadExtendsSample threadExtendSample = new ThreadExtendsSample(i);
            threadExtendSample.start();
        }

        /**
         * 实现Runnable
         */
        for (int i = 0; i < 10; i++) {
            ThreadRunnableSample threadRunnableSample = new ThreadRunnableSample(i);
            new Thread(threadRunnableSample,"Thread" + i).start();
        }

        /**
         * 匿名内部类
         */
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        try {
                            System.out.println("InnerClassThread [" + finalI + "] is running! " + j);
                            Thread.sleep(100);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start();
        }


        /**
         * lambda
         */
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        System.out.println("LambdaThread [" + finalI + "] is running! " + j);
                        Thread.sleep(100);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
