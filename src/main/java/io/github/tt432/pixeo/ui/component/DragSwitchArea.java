package io.github.tt432.pixeo.ui.component;

import io.github.tt432.pixeo.ui.UIElement;

/**
 * @author TT432
 */
public class DragSwitchArea extends UIComponent {
    @Override
    public boolean active() {
        return getOwner().hover(mouseX(), mouseY())
                || getOwner().getComponent(Draggable.class).map(Draggable::active).orElse(false);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        getOwner()
                .getCanvas()
                .searcher()
                .search(ele -> ele.hover(pMouseX, pMouseY) && ele.getComponent(DragTargetArea.class).isPresent())
                .lastLayer()
                .ifPresent(uiElements -> {
                    for (UIElement element : uiElements) {
                        if (element != getOwner().getParent() && element != getOwner()) {
                            getOwner().getCanvas().removeElement(getOwner());
                            element.addChild(getOwner());

                            // TODO 将 owner 设置为 child 后将位置设置为当前位置
                        }
                    }
                });

        return true;
    }
}
