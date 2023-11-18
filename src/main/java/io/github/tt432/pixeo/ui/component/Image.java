package io.github.tt432.pixeo.ui.component;

import io.github.tt432.pixeo.Pixeo;
import io.github.tt432.pixeo.util.FourPoint;
import io.github.tt432.pixeo.util.render.PixeoRenders;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector4f;

/**
 * @author TT432
 */
@Getter
@Setter
public class Image extends UIComponent {
    ResourceLocation texture = new ResourceLocation(Pixeo.MOD_ID, "textures/gui/blank.png");
    Vector4f color = new Vector4f(1);

    @Override
    public void render(GuiGraphics guiGraphics) {
        FourPoint fourPoint = getOwner().getComponent(RectTransform.class).orElse(RectTransform.EMPTY).getFourPoint();

        PixeoRenders.blit(texture, guiGraphics.pose(),
                fourPoint.getLeft(), fourPoint.getRight(), fourPoint.getUp(), fourPoint.getDown(), renderLayer(),
                0, 1, 0, 1, color.x, color.y, color.z, color.w);
    }
}
