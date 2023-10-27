package io.github.tt432.pixeo.ui.component;

import io.github.tt432.pixeo.ui.UIElement;
import io.github.tt432.pixeo.util.size.ConstantContainerBasedSize;
import io.github.tt432.pixeo.util.size.ConstantContentBasedSize;
import org.joml.Vector2f;

/**
 * @author TT432
 */
public abstract class SizeCalculator extends UIComponent {
    @Override
    public void updateLayout() {
        owner.getComponent(RectTransform.class).ifPresent(rt -> rt.setSize(calculateSize(owner)));
    }

    public abstract Vector2f calculateSize(UIElement owner);

    public static SizeCalculator constant(Vector2f size) {
        return new ConstantContentBasedSize(size);
    }

    public static SizeCalculator constant(int w, int h) {
        return constant(new Vector2f(w, h));
    }

    public static SizeCalculator constantContainerBased(Vector2f size) {
        return new ConstantContainerBasedSize(size);
    }
}
