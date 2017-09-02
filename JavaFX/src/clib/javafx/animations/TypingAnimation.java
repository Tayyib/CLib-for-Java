package clib.javafx.animations;


import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.util.Duration;


@SuppressWarnings("WeakerAccess")
public class TypingAnimation extends Transition
{
    private String text;
    private final Label label;


    public TypingAnimation(Label label, int duration)
    {
        this(label, duration, label.getText());
    }

    public TypingAnimation(Label label, int duration, String text)
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
