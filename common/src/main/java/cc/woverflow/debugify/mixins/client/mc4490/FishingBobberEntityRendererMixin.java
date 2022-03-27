package cc.woverflow.debugify.mixins.client.mc4490;

import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FishingBobberEntityRenderer.class)
public class FishingBobberEntityRendererMixin {
    @ModifyConstant(method = "render", constant = @Constant(floatValue = -0.1875F))
    private float renderSneakOffset(float constant) {
        return -0.2875F;
    }
}
