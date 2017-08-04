package clib.threading;


import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;


public abstract class SimpleThread extends Thread
{
    private static final List<SimpleThread> ACTIVE_THREADS = new ArrayList<>(0);
    private SimpleObjectProperty<ThreadExitCode> exitCode = new SimpleObjectProperty<>(ThreadExitCode.UNDEFINED);
    private boolean ableToRunOnMultipleInstances = false;
    private long interval = 0L;


    @Override
    public void start()
    {
        if (!ableToRunOnMultipleInstances)
        {
            for (SimpleThread thread : ACTIVE_THREADS)
            {
                if (thread.getClass().equals(this.getClass()))
                {
                    System.out.println("There is an instance running already!");
                    return;
                }
            }
        }

        super.start();
    }

    @Override
    public final void run()
    {
        ACTIVE_THREADS.add(this);

        try
        {
            while (task() && !isInterrupted()) Thread.sleep(interval);
            exitCode.set(ThreadExitCode.FINISHED);
        }
        catch (InterruptedException ignored)
        {
            exitCode.set(ThreadExitCode.INTERRUPTED);
        }
        finally
        {
            ACTIVE_THREADS.remove(this);
        }
    }

    // Getters

    public static List<SimpleThread> getActiveThreads()
    {
        return ACTIVE_THREADS;
    }

    public ThreadExitCode getExitCode()
    {
        return exitCode.get();
    }

    public SimpleObjectProperty<ThreadExitCode> exitCodeProperty()
    {
        return exitCode;
    }

    public boolean isAbleToRunOnMultipleInstances()
    {
        return ableToRunOnMultipleInstances;
    }

    public long getInterval()
    {
        return interval;
    }

    // Setters

    public void setAbleToRunOnMultipleInstances(boolean ableToRunOnMultipleInstances)
    {
        this.ableToRunOnMultipleInstances = ableToRunOnMultipleInstances;
    }

    /**
     * Sets the time interval in <b>millis</b> used by <code>Thread.sleep()</code> each time <code>task()</code> ended.
     */
    public void setInterval(long interval)
    {
        this.interval = interval;
    }

    // Abstract methods

    /**
     * <b><p>Insert your code here to run it in the thread.</p></b>
     * <p><code>return true;</code> allows the method to behave like a recursive method or a while loop.<br>
     * So, set return value as <code>true</code> if you want to repeat thread and <code>false</code> for vice versa.</p>
     *
     * @return <p>if this function will repeat (<code>true</code>) or not (<code>false</code>)<br> <b>Note that,
     * if the method always returns <code>true</code> you should use <code>interrupt()</code> to stop thread.</b></p>
     */
    public abstract boolean task() throws InterruptedException;
}
