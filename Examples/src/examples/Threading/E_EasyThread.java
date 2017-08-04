package examples.Threading;


import clib.threading.EasyThread;


public class E_EasyThread
{
    public static void main(String[] args)
    {
        Thread1 thread1 = new Thread1();
        thread1.setInterval(1000);

        EasyThread thread2 = new EasyThread()
        {
            @Override
            public boolean task() throws InterruptedException
            {
                System.out.println("[Thread 2] Hello! Waiting for 5 seconds...");
                Thread.sleep(5000);
                System.out.println("[Thread 2] Process has finished.!");
                return false;
            }
        };

        thread1.start();
        thread2.start();
    }
}


class Thread1 extends EasyThread
{
    private int i = 0;

    @Override
    public boolean task() throws InterruptedException
    {
        if (i > 7)
        {
            System.out.println("[Thread 1] Process has finished.");
            return false;
        }

        System.out.println(String.format("[Thread 1] Counting %s...", i));
        i++;
        return true;  // make infinite
    }
}
