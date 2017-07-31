package clib.javafx.transitions;


import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.util.Duration;


@SuppressWarnings("WeakerAccess")
public class TypingTransition extends Transition
{
    private String text;
    private final Label label;


    public TypingTransition(Label label, int duration)
    {
        this(label, duration, label.getText());
    }

    public TypingTransition(Label label, int duration, String text)
    {
        setCycleDuration(Duration.millis(duration));

        this.label = label;
        this.text = text;
    }

    @Override
    protected final void interpolate(double frac)
    {
        label.setText(text.substring(0, Math.round(text.length() * (float) frac)));
    }
}
