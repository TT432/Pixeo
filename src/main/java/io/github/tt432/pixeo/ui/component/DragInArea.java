package io.github.tt432.pixeo.ui.component;

/**
 * @author TT432
 */
public class DragInArea extends UIComponent {
    @Override
    public boolean active() {
        return firstHover() || getOwner().getComponent(Draggable.class).map(Draggable::active).orElse(false);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        // TODO 在确定该元素不在 parent 的 rect 中时 remove

        return false;
    }
}
