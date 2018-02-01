package clib.javafx.animations;


import org.jetbrains.annotations.Nullable;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.util.Duration;


public class ScaleTransition extends Transition
{
    private final Node node;
    private final double fromX;
    private final double fromY;
    private final double fromZ;
    private final double diffX;
    private final double diffY;
    private final double diffZ;


    public ScaleTransition(Node node, int duration, @Nullable Double toX, @Nullable Double toY, @Nullable Double toZ)
    {
        setCycleDuration(Duration.millis(duration));

        this.node = node;
        this.fromX = node.getScaleX();
        this.fromY = node.getScaleY();
        this.fromZ = node.getScaleZ();

        this.diffX = toX == null ? 0 : toX - fromX;
        this.diffY = toY == null ? 0 : toY - fromY;
        this.diffZ = toZ == null ? 0 : toZ - fromZ;
    }

    @Override
    protected void interpolate(double frac)
    {
        node.setScaleX(fromX + (diffX * frac));
        node.setScaleY(fromY + (diffY * frac));
        node.setScaleZ(fromZ + (diffZ * frac));
    }
}
