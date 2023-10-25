package io.github.tt432.pixeo.util;

import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author TT432
 */
public interface ProxyGuiEventListener extends GuiEventListener {
    List<? extends GuiEventListener> target();

    @Override
    default void mouseMoved(double pMouseX, double pMouseY) {
        for (GuiEventListener guiEventListener : target()) {
            guiEventListener.mouseMoved(pMouseX, pMouseY);
        }
    }

    @Override
    default boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        boolean result = false;

        for (GuiEventListener guiEventListener : target()) {
            if (guiEventListener.mouseClicked(pMouseX, pMouseY, pButton)) {
                result = true;
            }
        }

        return result;
    }

    @Override
    default boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        boolean result = false;

        for (GuiEventListener guiEventListener : target()) {
            if (guiEventListener.mouseReleased(pMouseX, pMouseY, pButton)) {
                result = true;
            }
        }

        return result;
    }

    @Override
    default boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        boolean result = false;

        for (GuiEventListener guiEventListener : target()) {
            if (guiEventListener.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY)) {
                result = true;
            }
        }

        return result;
    }

    @Override
    default boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        boolean result = false;

        for (GuiEventListener guiEventListener : target()) {
            if (guiEventListener.mouseScrolled(pMouseX, pMouseY, pDelta)) {
                result = true;
            }
        }

        return result;
    }

    @Override
    default boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        boolean result = false;

        for (GuiEventListener guiEventListener : target()) {
            if (guiEventListener.keyPressed(pKeyCode, pScanCode, pModifiers)) {
                result = true;
            }
        }

        return result;
    }

    @Override
    default boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
        boolean result = false;

        for (GuiEventListener guiEventListener : target()) {
            if (guiEventListener.keyReleased(pKeyCode, pScanCode, pModifiers)) {
                result = true;
            }
        }

        return result;
    }

    @Override
    default boolean charTyped(char pCodePoint, int pModifiers) {
        boolean result = false;

        for (GuiEventListener guiEventListener : target()) {
            if (guiEventListener.charTyped(pCodePoint, pModifiers)) {
                result = true;
            }
        }

        return result;
    }

    @Nullable
    @Override
    default ComponentPath nextFocusPath(FocusNavigationEvent pEvent) {
        return null;
    }

    @Override
    default boolean isMouseOver(double pMouseX, double pMouseY) {
        boolean result = false;

        for (GuiEventListener guiEventListener : target()) {
            if (guiEventListener.isMouseOver(pMouseX, pMouseY)) {
                result = true;
            }
        }

        return result;
    }

    @Override
    default void setFocused(boolean pFocused) {
    }

    @Override
    default boolean isFocused() {
        return false;
    }

    @Nullable
    @Override
    default ComponentPath getCurrentFocusPath() {
        return GuiEventListener.super.getCurrentFocusPath();
    }

    @Override
    default ScreenRectangle getRectangle() {
        return GuiEventListener.super.getRectangle();
    }

    @Override
    default int getTabOrderGroup() {
        return GuiEventListener.super.getTabOrderGroup();
    }
}
