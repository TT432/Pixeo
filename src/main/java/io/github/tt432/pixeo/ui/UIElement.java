package io.github.tt432.pixeo.ui;

import io.github.tt432.pixeo.ui.component.RectTransform;
import io.github.tt432.pixeo.ui.component.UIComponent;
import io.github.tt432.pixeo.util.ProxyGuiComponentEventListener;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author TT432
 */
@Getter
@RequiredArgsConstructor
public final class UIElement implements  ProxyGuiComponentEventListener {
    @NotNull
    Canvas canvas;
    @Nullable
    UIElement parent;
    List<UIElement> children = new ArrayList<>();

    Map<Class<?>, UIComponent> components = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> Optional<T> getComponent(Class<T> componentClass) {
        return Optional.ofNullable(components.get(componentClass)).map(c -> (T) c);
    }

    public void addComponent(UIComponent component) {
        components.put(component.getClass(), component);
    }

    public boolean hover(double mouseX, double mouseY) {
        return getComponent(RectTransform.class)
                .map(rt -> rt.getFourPoint().inside(mouseX, mouseY))
                .orElse(false);
    }

    public void updateLayout() {
        for (UIComponent value : components.values()) {
            value.updateLayout();
        }

        for (UIElement child : children) {
            child.updateLayout();
        }
    }

    public void render(GuiGraphics guiGraphics) {
        for (UIComponent value : components.values()) {
            if (value.active())
                value.beforeRender(guiGraphics);
        }

        for (UIComponent value : components.values()) {
            if (value.active())
                value.render(guiGraphics);
        }

        for (UIElement child : children) {
            child.render(guiGraphics);
        }

        for (UIComponent value : components.values()) {
            if (value.active())
                value.afterRender(guiGraphics);
        }
    }

    public void setup() {
        for (UIComponent value : components.values()) {
            value.setup(this);
        }

        for (UIElement child : children) {
            child.setup();
        }
    }

    @Override
    public Collection<UIComponent> components() {
        return components.values();
    }

    @Override
    public List<? extends GuiEventListener> target() {
        return children;
    }
}
