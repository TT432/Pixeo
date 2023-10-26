package io.github.tt432.pixeo.ui;

import io.github.tt432.pixeo.ui.component.UIComponent;
import io.github.tt432.pixeo.util.ProxyGuiEventListener;
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
public final class UIElement implements ProxyGuiEventListener {
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

    public void render(GuiGraphics guiGraphics) {
        for (UIComponent value : components.values()) {
            value.setupLayout(this);
        }

        for (UIComponent value : components.values()) {
            value.beforeRender(guiGraphics);
        }

        for (UIComponent value : components.values()) {
            value.render(guiGraphics);
        }

        for (UIElement child : children) {
            child.render(guiGraphics);
        }

        for (UIComponent value : components.values()) {
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
    public List<? extends GuiEventListener> target() {
        ArrayList<GuiEventListener> result = new ArrayList<>(children);
        result.addAll(components.values());
        return result;
    }
}
