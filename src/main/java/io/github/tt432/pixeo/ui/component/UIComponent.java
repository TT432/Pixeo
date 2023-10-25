package io.github.tt432.pixeo.ui.component;

import io.github.tt432.pixeo.ui.element.UIElement;
import lombok.Getter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;

/**
 * @author TT432
 */
public abstract class UIComponent implements GuiEventListener {
    @Getter
    UIElement owner;

    @Override
    public void setFocused(boolean pFocused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }

    public void setupLayout(UIElement owner) {

    }

    public void setup(UIElement owner) {
        this.owner = owner;
    }

    public void beforeRender(GuiGraphics guiGraphics) {

    }

    public void afterRender(GuiGraphics guiGraphics) {

    }
}
