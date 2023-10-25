package io.github.tt432.pixeo.ui.component;

import io.github.tt432.pixeo.util.FourPoint;
import net.minecraft.client.gui.GuiGraphics;
import org.lwjgl.opengl.GL11;

/**
 * @author TT432
 */
public class Clip extends UIComponent{
    @Override
    public void beforeRender(GuiGraphics guiGraphics) {
        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        getOwner().getComponent(RectTransform.class).ifPresent(rt -> {
            FourPoint fourPoint = rt.fourPoint.toActualPoint();
            GL11.glScissor((int) fourPoint.getLeft(), (int) fourPoint.getUp(),
                    (int) (fourPoint.getRight() - fourPoint.getLeft()), (int) (fourPoint.getDown() - fourPoint.getUp()));
        });
    }

    @Override
    public void afterRender(GuiGraphics guiGraphics) {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
