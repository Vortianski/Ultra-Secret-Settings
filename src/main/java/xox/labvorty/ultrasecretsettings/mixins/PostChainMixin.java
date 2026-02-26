package xox.labvorty.ultrasecretsettings.mixins;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(PostChain.class)
public class PostChainMixin {
    @Shadow
    @Final
    private Map<String, RenderTarget> customRenderTargets;

    @Unique
    private RenderTarget ultrasecret_previousTarget;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void ultrasecret$initPrevious(TextureManager textureManager, ResourceProvider resourceProvider, RenderTarget screenTarget, ResourceLocation resourceLocation, CallbackInfo ci) {
        int width = screenTarget.width;
        int height = screenTarget.height;
        ultrasecret_previousTarget = new TextureTarget(width, height, true, Minecraft.ON_OSX);
        ultrasecret_previousTarget.setClearColor(0f, 0f, 0f, 0f);

        customRenderTargets.put("previous", ultrasecret_previousTarget);
    }
}
