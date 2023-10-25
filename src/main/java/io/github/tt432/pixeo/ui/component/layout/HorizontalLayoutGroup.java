package io.github.tt432.pixeo.ui.component.layout;

import io.github.tt432.pixeo.ui.component.UIComponent;
import org.joml.Vector4f;

/**
 * @author TT432
 */
public class HorizontalLayoutGroup extends UIComponent {
    /**
     * 边缘到元素的间距。
     * <br/>
     * up -> right -> down -> left
     */
    Vector4f padding;

    /**
     * 元素的间距
     */
    float spacing;
}
