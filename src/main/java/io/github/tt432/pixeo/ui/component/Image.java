package io.github.tt432.pixeo.ui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import io.github.tt432.pixeo.Pixeo;
import io.github.tt432.pixeo.util.FourPoint;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
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

        innerBlit(texture, guiGraphics.pose(),
                fourPoint.getLeft(), fourPoint.getRight(), fourPoint.getUp(), fourPoint.getDown(), 0,
                0, 1, 0, 1, color.x, color.y, color.z, color.w);
    }

    void innerBlit(ResourceLocation pAtlasLocation, PoseStack pose, float pX1, float pX2, float pY1, float pY2,
                   int pBlitOffset, float pMinU, float pMaxU, float pMinV, float pMaxV,
                   float pRed, float pGreen, float pBlue, float pAlpha) {
        RenderSystem.setShaderTexture(0, pAtlasLocation);
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.enableBlend();
        Matrix4f matrix4f = pose.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
        bufferbuilder.vertex(matrix4f, pX1, pY1, pBlitOffset).color(pRed, pGreen, pBlue, pAlpha).uv(pMinU, pMinV).endVertex();
        bufferbuilder.vertex(matrix4f, pX1, pY2, pBlitOffset).color(pRed, pGreen, pBlue, pAlpha).uv(pMinU, pMaxV).endVertex();
        bufferbuilder.vertex(matrix4f, pX2, pY2, pBlitOffset).color(pRed, pGreen, pBlue, pAlpha).uv(pMaxU, pMaxV).endVertex();
        bufferbuilder.vertex(matrix4f, pX2, pY1, pBlitOffset).color(pRed, pGreen, pBlue, pAlpha).uv(pMaxU, pMinV).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        RenderSystem.disableBlend();
    }
}
