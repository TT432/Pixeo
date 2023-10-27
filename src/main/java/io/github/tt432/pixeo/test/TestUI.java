package io.github.tt432.pixeo.test;

import io.github.tt432.pixeo.ui.Canvas;
import io.github.tt432.pixeo.ui.PixeoScreen;
import io.github.tt432.pixeo.ui.UIElement;
import io.github.tt432.pixeo.ui.component.*;
import io.github.tt432.pixeo.util.Anchor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

/**
 * @author TT432
 */
@Mod.EventBusSubscriber(Dist.CLIENT)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUI {
    @SubscribeEvent
    public static void onEvent(TickEvent.ClientTickEvent event) {
        if (GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_G) == GLFW.GLFW_PRESS) {
            Canvas canvas = new Canvas();

            UIElement background = new UIElement(canvas);
            Image bg = new Image();
            bg.setColor(new Vector4f(0, 0, 1, 1));
            background.addComponent(bg);
            background.addComponent(new RectTransform(Anchor.CENTER_CENTER));
            background.addComponent(SizeCalculator.constant(new Vector2f(200, 200)));
            background.addComponent(new Clip());
            background.addComponent(new DragTargetArea());
            canvas.addElement(background);

            UIElement element = new UIElement(canvas);
            Image image = new Image();
            image.setColor(new Vector4f(1, 0, 0, 1));
            element.addComponent(image);
            element.addComponent(new RectTransform(Anchor.CENTER_CENTER));
            element.addComponent(SizeCalculator.constant(new Vector2f(40, 40)));
            element.addComponent(new Draggable());
            background.addChild(element);

            UIElement testB = new UIElement(canvas);
            testB.addComponent(new RectTransform(Anchor.RIGHT_CENTER));
            testB.addComponent(SizeCalculator.constant(50, 50));
            Image image1 = new Image();
            image1.setColor(new Vector4f(0, 1, 1, 1));
            testB.addComponent(image1);
            testB.addComponent(new CreateOnDrag(cod -> {
                UIElement copied = new UIElement(canvas);
                canvas.addElement(copied);
                copied.addComponent(new RectTransform(Anchor.RIGHT_CENTER));
                copied.addComponent(SizeCalculator.constant(50, 50));
                Draggable draggable = new Draggable();
                copied.addComponent(draggable);
                Image component = new Image();
                component.setColor(new Vector4f(0, 1, 0, 1));
                copied.addComponent(component);
                copied.addComponent(new DragSwitchArea());
                copied.addComponent(new DragInArea());

                copied.setup();
                copied.updateLayout();
                draggable.mouseClicked(mouseX(), mouseY(), 0);

                return copied;
            }));
            canvas.addElement(testB);

            Minecraft.getInstance().setScreen(new PixeoScreen(Component.empty(), canvas));
        }
    }

    private static double mouseX() {
        return Minecraft.getInstance().mouseHandler.xpos()
                * Minecraft.getInstance().getWindow().getGuiScaledWidth()
                / Minecraft.getInstance().getWindow().getScreenWidth();
    }

    private static double mouseY() {
        return Minecraft.getInstance().mouseHandler.ypos()
                * Minecraft.getInstance().getWindow().getGuiScaledHeight()
                / Minecraft.getInstance().getWindow().getScreenHeight();
    }
}
