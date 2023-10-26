package io.github.tt432.pixeo.util.size;

import io.github.tt432.pixeo.ui.UIElement;
import io.github.tt432.pixeo.ui.component.SizeCalculator;
import lombok.AllArgsConstructor;
import org.joml.Vector2f;

/**
 * @author TT432
 */
@AllArgsConstructor
public class ConstantContentBasedSize extends SizeCalculator {
    Vector2f size;

    @Override
    public Vector2f calculateSize(UIElement owner) {
        return size;
    }
}
