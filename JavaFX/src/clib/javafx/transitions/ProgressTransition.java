package clib.javafx.transitions;


import javafx.animation.Transition;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;


public class ProgressTransition extends Transition
{
    private final ProgressBar progressBar;
    private final double fromValue;
    private final double diff;


    public ProgressTransition(ProgressBar progressBar, int duration, double toValue)
    {
        setCycleDuration(Duration.millis(duration));

        this.progressBar = progressBar;
        this.fromValue = progressBar.getProgress();
        this.diff = toValue - fromValue;
    }

    @Override
    protected final void interpolate(double frac)
    {
        progressBar.setProgress(fromValue + (diff * frac));
    }
}
