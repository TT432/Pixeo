package io.github.tt432.pixeo.util;

import io.github.tt432.pixeo.ui.component.UIComponent;

import java.util.Collection;

/**
 * @author TT432
 */
public interface ProxyGuiComponentEventListener extends ProxyGuiEventListener {
    Collection<UIComponent> components();

    @Override
    default void mouseMoved(double pMouseX, double pMouseY) {
        ProxyGuiEventListener.super.mouseMoved(pMouseX, pMouseY);

        for (var guiEventListener : components()) {
            if (guiEventListener.active()) {
                guiEventListener.mouseMoved(pMouseX, pMouseY);
            }
        }
    }

    @Override
    default boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        ProxyGuiEventListener.super.mouseClicked(pMouseX, pMouseY, pButton);

        boolean result = false;

        for (var guiEventListener : components()) {
            if (guiEventListener.active() && (guiEventListener.mouseClicked(pMouseX, pMouseY, pButton))) {
                result = true;
            }
        }

        return result;
    }

    @Override
    default boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        ProxyGuiEventListener.super.mouseReleased(pMouseX, pMouseY, pButton);

        boolean result = false;

        for (var guiEventListener : components()) {
            if (guiEventListener.active() && (guiEventListener.mouseReleased(pMouseX, pMouseY, pButton))) {
                result = true;
            }
        }

        return result;
    }

    @Override
    default boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        ProxyGuiEventListener.super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);

        boolean result = false;

        for (var guiEventListener : components()) {
            if (guiEventListener.active() && (guiEventListener.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY))) {
                result = true;
            }
        }

        return result;
    }

    @Override
    default boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        ProxyGuiEventListener.super.mouseScrolled(pMouseX, pMouseY, pDelta);

        boolean result = false;

        for (var guiEventListener : components()) {
            if (guiEventListener.active() && (guiEventListener.mouseScrolled(pMouseX, pMouseY, pDelta))) {
                result = true;
            }
        }

        return result;
    }

    @Override
    default boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        ProxyGuiEventListener.super.keyPressed(pKeyCode, pScanCode, pModifiers);

        boolean result = false;

        for (var guiEventListener : components()) {
            if (guiEventListener.active() && (guiEventListener.keyPressed(pKeyCode, pScanCode, pModifiers))) {
                result = true;
            }
        }

        return result;
    }

    @Override
    default boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
        ProxyGuiEventListener.super.keyReleased(pKeyCode, pScanCode, pModifiers);

        boolean result = false;

        for (var guiEventListener : components()) {
            if (guiEventListener.active() && (guiEventListener.keyReleased(pKeyCode, pScanCode, pModifiers))) {
                result = true;
            }
        }

        return result;
    }

    @Override
    default boolean charTyped(char pCodePoint, int pModifiers) {
        ProxyGuiEventListener.super.charTyped(pCodePoint, pModifiers);

        boolean result = false;

        for (var guiEventListener : components()) {
            if (guiEventListener.active() && (guiEventListener.charTyped(pCodePoint, pModifiers))) {
                result = true;
            }
        }

        return result;
    }

    @Override
    default boolean isMouseOver(double pMouseX, double pMouseY) {
        ProxyGuiEventListener.super.isMouseOver(pMouseX, pMouseY);

        boolean result = false;

        for (var guiEventListener : components()) {
            if (guiEventListener.active() && (guiEventListener.isMouseOver(pMouseX, pMouseY))) {
                result = true;
            }
        }

        return result;
    }
}
