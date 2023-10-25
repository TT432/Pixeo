package io.github.tt432.pixeo.ui.component;

/**
 * @author TT432
 */
public class Draggable extends UIComponent {
    boolean dragging;

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        dragging = true;
        return true;
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (dragging) {
            owner.getComponent(RectTransform.class).ifPresent(rt -> rt.getOffset().add((float) pDragX, (float) pDragY));
        }

        return true;
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        dragging = false;
        return true;
    }
}
