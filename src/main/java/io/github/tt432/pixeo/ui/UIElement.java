package io.github.tt432.pixeo.ui;

import io.github.tt432.pixeo.ui.component.RectTransform;
import io.github.tt432.pixeo.ui.component.UIComponent;
import io.github.tt432.pixeo.util.ProxyGuiComponentEventListener;
import io.github.tt432.pixeo.util.Sorts;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author TT432
 */
@RequiredArgsConstructor
public final class UIElement implements ProxyGuiComponentEventListener {
    @NotNull
    @Getter
    final String name;
    @NotNull
    @Getter
    final Canvas canvas;
    @Nullable
    @Getter
    UIElement parent;
    @Setter
    @Getter
    int layer;
    @Getter
    List<UIElement> children = new ArrayList<>();

    List<UIElement> addChildQueue = new ArrayList<>();

    public void addChild(UIElement child) {
        addChildQueue.add(child);
    }

    private final Map<Class<?>, UIComponent> components = new LinkedHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> Optional<T> getComponent(Class<T> componentClass) {
        return Optional.ofNullable(components.get(componentClass)).map(c -> (T) c);
    }

    public void addComponent(UIComponent component) {
        components.put(component.getClass(), component);

        List<UIComponent> sortComponents = Sorts.sortComponents(components.values());

        components.clear();

        for (UIComponent sortComponent : sortComponents) {
            components.put(sortComponent.getClass(), sortComponent);
        }
    }

    public boolean hover(double mouseX, double mouseY) {
        return getComponent(RectTransform.class)
                .map(rt -> rt.getFourPoint().inside(mouseX, mouseY))
                .orElse(false);
    }

    public boolean parentHover(double mouseX, double mouseY) {
        return getParent() == null || getParent().hover(mouseX, mouseY);
    }

    public void updateLayout() {
        for (UIComponent value : components.values()) {
            value.updateLayout();
        }

        for (UIElement child : children) {
            child.updateLayout();
        }
    }

    public void processElementsModify() {
        processAddChildren();

        for (UIElement child : children) {
            child.processElementsModify();
        }
    }

    private void processAddChildren() {
        if (!addChildQueue.isEmpty()) {
            for (UIElement element : addChildQueue) {
                children.add(element);
                element.parent = this;
                element.setup();
            }

            addChildQueue.clear();
            getCanvas().updateLayers();
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
        processAddChildren();

        for (UIComponent value : components.values()) {
            value.setup(this);
        }

        for (UIElement child : children) {
            child.setup();
        }
    }

    @Override
    public Collection<UIComponent> components() {
        return new ArrayList<>(components.values());
    }

    @Override
    public List<? extends GuiEventListener> target() {
        return new ArrayList<>(children);
    }
}
