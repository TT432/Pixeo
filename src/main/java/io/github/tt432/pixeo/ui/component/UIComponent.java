package io.github.tt432.pixeo.ui.component;

import io.github.tt432.pixeo.ui.UIElement;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TT432
 */
public abstract class UIComponent implements GuiEventListener {
    protected UIElement owner;

    /**
     * this before the list
     */
    @Getter
    List<Class<? extends UIComponent>> before = new ArrayList<>();
    /**
     * this after the list
     */
    @Getter
    List<Class<? extends UIComponent>> after = new ArrayList<>();

    public boolean active() {
        return true;
    }

    protected double mouseX() {
        return Minecraft.getInstance().mouseHandler.xpos()
                * Minecraft.getInstance().getWindow().getGuiScaledWidth()
                / Minecraft.getInstance().getWindow().getScreenWidth();
    }

    protected double mouseY() {
        return Minecraft.getInstance().mouseHandler.ypos()
                * Minecraft.getInstance().getWindow().getGuiScaledHeight()
                / Minecraft.getInstance().getWindow().getScreenHeight();
    }

    @Override
    public void setFocused(boolean pFocused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }

    public void updateLayout() {

    }

    public void setup(UIElement owner) {
        this.owner = owner;
    }

    public void beforeRender(GuiGraphics guiGraphics) {

    }

    public void afterRender(GuiGraphics guiGraphics) {

    }

    public void render(GuiGraphics guiGraphics) {

    }

    public UIElement getOwner() {
        return this.owner;
    }
}
