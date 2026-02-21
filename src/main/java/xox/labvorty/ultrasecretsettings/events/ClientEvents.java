package xox.labvorty.ultrasecretsettings.events;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import xox.labvorty.shaderite.shader.PostShaderRunnerHandler;
import xox.labvorty.ultrasecretsettings.data.SuperSecretSettingsData;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void tick(ClientTickEvent.Post event) {
        PostShaderRunnerHandler.setShader(ResourceLocation.parse(SuperSecretSettingsData.currentShader));
        PostShaderRunnerHandler.setRun(SuperSecretSettingsData.shadersActive);
    }
}
