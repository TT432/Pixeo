package io.github.tt432.pixeo.ui.component;

import io.github.tt432.pixeo.ui.UIElement;

/**
 * @author TT432
 */
public class DragSwitchArea extends UIComponent {
    public DragSwitchArea() {
        before.add(Draggable.class);
    }

    @Override
    public boolean active() {
        return firstHover() || getOwner().getComponent(Draggable.class).map(Draggable::active).orElse(false);
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

                            // TODO 修改 area 后保证 element 位于鼠标下方
                            // getOwner().getComponent(RectTransform.class).ifPresent(rt -> {
                            //     Vector2f oldAnchorPoint = rt.anchorPoint(getOwner().getParent());
                            //     Vector2f newAnchorPoint = rt.anchorPoint(element);
                            //     Vector2f offsetAnchorPoint = newAnchorPoint.sub(oldAnchorPoint, new Vector2f());
                            //     rt.offset.add(offsetAnchorPoint);
                            // });
                        }
                    }
                });

        return true;
    }
}
