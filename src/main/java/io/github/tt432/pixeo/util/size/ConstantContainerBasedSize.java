package io.github.tt432.pixeo.util.size;

import io.github.tt432.pixeo.ui.UIElement;
import io.github.tt432.pixeo.ui.component.RectTransform;
import io.github.tt432.pixeo.ui.component.SizeCalculator;
import org.joml.Vector2f;

/**
 * @author TT432
 */
public class ConstantContainerBasedSize extends SizeCalculator {
    Vector2f size;

    public ConstantContainerBasedSize(Vector2f size) {
        this.size = size.mul(2);
    }

    @Override
    public Vector2f calculateSize(UIElement owner) {
        return (owner.getParent() == null
                ? owner.getCanvas().getActualResolution()
                : owner.getParent().getComponent(RectTransform.class).map(RectTransform::getSize).orElse(new Vector2f()))
                .sub(size, new Vector2f());
    }
}
