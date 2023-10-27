package io.github.tt432.pixeo.ui;

import com.mojang.blaze3d.platform.Window;
import io.github.tt432.pixeo.util.ProxyGuiEventListener;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

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

    private final List<UIElement> addElementQueue = new ArrayList<>();

    public void addElement(UIElement element) {
        addElementQueue.add(element);
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

        // TODO
    }

    private void processAddElements() {
        for (UIElement element : addElementQueue) {
            element.setup();
        }
        elements.addAll(addElementQueue);
        addElementQueue.clear();
    }

    public void updateLayout() {
        processAddElements();

        updateSize();
        calculateRatio();

        for (UIElement element : elements) {
            element.updateLayout();
        }

        //TODO
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
        return elements;
    }
}
