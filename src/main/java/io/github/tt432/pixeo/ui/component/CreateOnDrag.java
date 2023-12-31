package io.github.tt432.pixeo.ui.component;

import io.github.tt432.pixeo.ui.UIElement;

import java.util.function.Function;

/**
 * @author TT432
 */
public class CreateOnDrag extends UIComponent {
    final Function<CreateOnDrag, UIElement> copyFactory;

    public CreateOnDrag(Function<CreateOnDrag, UIElement> copyFactory) {
        this.copyFactory = copyFactory;
    }

    boolean clicked;

    @Override
    public boolean active() {
        return firstHover();
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        clicked = true;
        return true;
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (clicked) {
            copyFactory.apply(this);
            clicked = false;
            return true;
        }

        return false;
    }

    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
        clicked = false;
        return true;
    }
}
