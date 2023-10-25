package io.github.tt432.pixeo.test;

import io.github.tt432.pixeo.ui.Canvas;
import io.github.tt432.pixeo.ui.PixeoScreen;
import io.github.tt432.pixeo.ui.component.Clip;
import io.github.tt432.pixeo.ui.component.Draggable;
import io.github.tt432.pixeo.ui.component.RectTransform;
import io.github.tt432.pixeo.ui.component.SizeCalculator;
import io.github.tt432.pixeo.ui.element.Image;
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

            Image bg = new Image(canvas);
            bg.setColor(new Vector4f(0, 0, 1, 1));
            bg.addComponent(new RectTransform(Anchor.CENTER_CENTER));
            bg.addComponent(SizeCalculator.constant(new Vector2f(200, 200)));
            bg.addComponent(new Clip());
            canvas.getElements().add(bg);

            Image image = new Image(canvas);
            image.setColor(new Vector4f(1, 0, 0, 1));
            image.addComponent(new RectTransform(Anchor.CENTER_CENTER));
            image.addComponent(SizeCalculator.constant(new Vector2f(40, 40)));
            image.addComponent(new Draggable());
            bg.getChildren().add(image);

            Minecraft.getInstance().setScreen(new PixeoScreen(Component.empty(), canvas));
        }
    }
}
