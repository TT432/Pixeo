package io.github.tt432.pixeo.ui;

import com.mojang.blaze3d.platform.Window;
import io.github.tt432.pixeo.util.ElementsSearchHandler;
import io.github.tt432.pixeo.util.ProxyGuiEventListener;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author TT432
 */
public class Canvas implements ProxyGuiEventListener {
    /**
     * 将会尝试用该分辨率作为 UI 的标准化大小。
     * <br/>
     * 当 x 或 y 不够大时将尝试缩小 {@link Canvas} 以使能够显示足够多的内容。
     */
    @Getter
    Vector2f targetResolution;
    List<UIElement> elements = new ArrayList<>();
    @Getter
    Vector2f actualResolution = new Vector2f();
    /**
     * actualResolution / targetResolution
     */
    @Getter
    float scaleRatio;

    @Getter
    private final LayerManager layerManager = new LayerManager();
    private ElementsSearchHandler searchHandler;

    public ElementsSearchHandler searcher() {
        if (searchHandler == null) {
            searchHandler = new ElementsSearchHandler(layerManager);
        }

        return searchHandler;
    }

    private final List<UIElement> addElementQueue = new ArrayList<>();

    public void addElement(UIElement element) {
        addElementQueue.add(element);
    }

    private final List<UIElement> removeElementQueue = new CopyOnWriteArrayList<>();

    public void removeElement(UIElement element) {
        removeElementQueue.add(element);
    }

    public void setup() {
        if (targetResolution == null) {
            Window window = Minecraft.getInstance().getWindow();
            targetResolution = new Vector2f(window.getGuiScaledWidth(), window.getGuiScaledHeight());
        }

        processAddElements();
    }

    public Canvas setTargetResolution(float width, float height) {
        if (targetResolution == null) {
            targetResolution = new Vector2f();
        }

        targetResolution.set(width, height);
        return this;
    }

    public void render(GuiGraphics guiGraphics) {
        updateLayout();

        for (UIElement element : elements) {
            element.render(guiGraphics);
        }
    }

    private void processAddElements() {
        for (UIElement element : addElementQueue) {
            element.setup();
        }
        elements.addAll(addElementQueue);
        addElementQueue.clear();

        updateLayers();
    }

    public void updateLayers() {
        layerManager.updateElementLayers(elements);
    }

    private void processRemoveElements() {
        for (UIElement element : removeElementQueue) {
            if (element.parent == null) {
                elements.remove(element);
            } else {
                element.parent.children.remove(element);
            }
        }

        removeElementQueue.clear();

        updateLayers();
    }

    private void processElementsModify() {
        processRemoveElements();
        processAddElements();

        for (UIElement element : elements) {
            element.processElementsModify();
        }
    }

    public void updateLayout() {
        processElementsModify();

        updateSize();
        calculateRatio();

        for (UIElement element : elements) {
            element.updateLayout();
        }
    }

    private void updateSize() {
        Window window = Minecraft.getInstance().getWindow();
        actualResolution.set(window.getGuiScaledWidth(), window.getGuiScaledHeight());
    }

    private void calculateRatio() {
        scaleRatio = Math.min(actualResolution.x / targetResolution.x, actualResolution.y / targetResolution.y);
    }

    @Override
    public List<? extends GuiEventListener> target() {
        return new ArrayList<>(elements);
    }
}
