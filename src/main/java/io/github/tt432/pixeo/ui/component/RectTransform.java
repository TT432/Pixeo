package io.github.tt432.pixeo.ui.component;

import io.github.tt432.pixeo.ui.UIElement;
import io.github.tt432.pixeo.util.Anchor;
import io.github.tt432.pixeo.util.FourPoint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import org.joml.Vector4f;

/**
 * @author TT432
 */
@Getter
@RequiredArgsConstructor
public class RectTransform extends UIComponent {
    public static final RectTransform EMPTY = new RectTransform(Anchor.CENTER_CENTER);

    @NotNull
    Anchor anchor;
    Vector2f offset = new Vector2f();
    @Setter
    Vector2f size = new Vector2f();
    FourPoint fourPoint = new FourPoint();

    @Override
    public void updateLayout() {
        calculateFourPoints();
    }

    public Vector2f anchorPoint(@Nullable UIElement parent) {
        Vector4f parentFourPoints;

        if (parent == null) {
            Vector2f actualResolution = owner.getCanvas().getActualResolution();

            parentFourPoints = new Vector4f(0, actualResolution.x, actualResolution.y, 0);
        } else {
            parentFourPoints = parent.getComponent(RectTransform.class).map(rt -> rt.fourPoint.vec4f()).orElse(new Vector4f());
        }

        final float parentLeft = parentFourPoints.w;
        final float parentRight = parentFourPoints.y;
        final float parentUp = parentFourPoints.x;
        final float parentDown = parentFourPoints.z;

        return new Vector2f(
                switch (anchor) {
                    case LEFT_UP, LEFT_CENTER, LEFT_DOWN -> parentLeft;
                    case CENTER_UP, CENTER_CENTER, CENTER_DOWN -> ((parentRight - parentLeft) / 2) + parentLeft;
                    case RIGHT_UP, RIGHT_DOWN, RIGHT_CENTER -> parentRight;
                },
                switch (anchor) {
                    case LEFT_UP, CENTER_UP, RIGHT_UP -> parentUp;
                    case LEFT_CENTER, CENTER_CENTER, RIGHT_CENTER -> ((parentDown - parentUp) / 2) + parentUp;
                    case LEFT_DOWN, CENTER_DOWN, RIGHT_DOWN -> parentDown;
                }
        );
    }

    public void calculateFourPoints() {
        Vector2f vector2f = anchorPoint(owner.getParent());

        var up = vector2f.y + offset.y - (size.y / 2);

        var right = vector2f.x + offset.x + (size.x / 2);

        var down = vector2f.y + offset.y + (size.y / 2);

        var left = vector2f.x + offset.x - (size.x / 2);

        fourPoint.set(up, down, left, right);
    }
}
