package io.github.tt432.pixeo.util;

import com.mojang.blaze3d.platform.Window;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minecraft.client.Minecraft;
import org.joml.Vector4f;

/**
 * @author TT432
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FourPoint {
    float left;
    float right;
    float up;
    float down;

    public Vector4f vec4f() {
        return new Vector4f(up, right, down, left);
    }

    public boolean inside(double x, double y) {
        return x >= left && x <= right
                && y >= up && y <= down;
    }

    public void set(float up, float down, float left, float right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public FourPoint toActualPoint() {
        Window window = Minecraft.getInstance().getWindow();

        return new FourPoint(
                (float) (left * window.getGuiScale()),
                (float) (right * window.getGuiScale()),
                (float) (up * window.getGuiScale()),
                (float) (down * window.getGuiScale())
        );
    }
}
