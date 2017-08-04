package examples.Threading;


import clib.threading.SimpleThread;
import clib.threading.ThreadExitCode;


public class E_SimpleThread
{
    public static void main(String[] args)
    {
        Thread1 thread1 = new Thread1();
        thread1.setInterval(1000);

        SimpleThread thread2 = new SimpleThread()
        {
            @Override
            public boolean task() throws InterruptedException
            {
                System.out.println("[Thread 2] Sorry! I am to put a limit, you have only 5 seconds!");
                Thread.sleep(5500);
                thread1.interrupt();
                return false;
            }
        };

        thread1.exitCodeProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.equals(ThreadExitCode.INTERRUPTED))
            {
                System.out.println("[LISTENER 1] has been interrupted by Thread 2 (-_-)");
            }
        });

        thread2.exitCodeProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.equals(ThreadExitCode.FINISHED))
            {
                System.out.println("[LISTENER 2] You've crossed the line!");
            }
        });

        thread1.start();
        thread2.start();
    }
}


class Thread1 extends SimpleThread
{
    private int i = 1;

    @Override
    public void start()
    {
        System.out.println("[THREAD 1] Hello! I'll count up to 10.");
        super.start();
    }

    @Override
    public boolean task() throws InterruptedException
    {
        if (i > 10)
        {
            System.out.println("[Thread 1] That's it.");
            return false;
        }

        System.out.println(String.format("[Thread 1] Counting %s...", i));
        i++;
        return true;  // make infinite
    }
}
