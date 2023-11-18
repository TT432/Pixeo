package io.github.tt432.pixeo.ui.component;

import io.github.tt432.pixeo.ui.UIElement;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author TT432
 */
@Getter
public abstract class UIComponent implements GuiEventListener {
    protected UIElement owner;

    /**
     * this before the list
     */
    List<Class<? extends UIComponent>> before = new ArrayList<>();

    /**
     * this after the list
     */
    List<Class<? extends UIComponent>> after = new ArrayList<>();

    public boolean active() {
        return true;
    }

    protected final int renderLayer() {
        return owner.getLayer() * 100;
    }

    protected final boolean parentHover() {
        return getOwner().getParent() == null || getOwner().getParent().hover(mouseX(), mouseY());
    }

    protected final boolean firstHover() {
        AtomicBoolean hasResult = new AtomicBoolean(false);
        AtomicBoolean result = new AtomicBoolean(false);

        double mouseX = mouseX();
        double mouseY = mouseY();

        getOwner().getCanvas().getLayerManager().forEachNegate((i, ls) -> {
            if (hasResult.get()) {
                return;
            }

            for (UIElement l : ls) {
                if (l.hover(mouseX, mouseY) && l.parentHover(mouseX, mouseY)) {
                    hasResult.set(true);

                    if (l == getOwner()) {
                        result.set(true);
                    }
                }
            }
        });

        return result.get();
    }

    protected final double mouseX() {
        return Minecraft.getInstance().mouseHandler.xpos()
                * Minecraft.getInstance().getWindow().getGuiScaledWidth()
                / Minecraft.getInstance().getWindow().getScreenWidth();
    }

    protected final double mouseY() {
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

}
