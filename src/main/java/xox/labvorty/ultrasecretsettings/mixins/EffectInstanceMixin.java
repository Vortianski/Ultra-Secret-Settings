package xox.labvorty.ultrasecretsettings.mixins;

import net.minecraft.client.renderer.EffectInstance;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EffectInstance.class)
public class EffectInstanceMixin {
    @Redirect(
            method = "updateLocations",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"
            )
    )
    private void silenceLogger(Logger instance, String s, Object oi, Object oii) {

    }
}
