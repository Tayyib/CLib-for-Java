package clib.javafx.animations;


import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.util.Duration;


public class FadeTransition extends Transition
{
    private final Node node;
    private final double fromValue;
    private final double diff;


    public FadeTransition(Node node, int duration, double toValue)
    {
        this(node, duration, node.getOpacity(), toValue);
    }

    public FadeTransition(Node node, int duration, double fromValue, double toValue)
    {
        setCycleDuration(Duration.millis(duration));

        this.node = node;
        this.fromValue = fromValue;
        this.diff = toValue - fromValue;
    }

    @Override
    protected final void interpolate(double frac)
    {
        node.setOpacity(fromValue + (diff * frac));
    }
}
