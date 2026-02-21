package xox.labvorty.ultrasecretsettings.mixins;

import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xox.labvorty.ultrasecretsettings.accessors.OptionsListAccessor;
import xox.labvorty.ultrasecretsettings.gui.screen.SuperSecretSettingsScreen;

@Mixin(VideoSettingsScreen.class)
public abstract class VideoSettingsScreenMixin extends OptionsSubScreen {
    public VideoSettingsScreenMixin(Screen lastScreen, Options options, Component title) {
        super(lastScreen, options, title);
    }

    @Inject(
            method = "addOptions",
            at = @At("HEAD")
    )
    private void ultrasecretsettings$addOption(CallbackInfo ci) {
        OptionsList optionsList = this.list;

        Button button = Button.builder(
                Component.literal("Super Secret Settings"),
                btn -> {
                    this.minecraft.setScreen(
                            new SuperSecretSettingsScreen(
                                    this.minecraft.screen
                            )
                    );
                }
        )
                .width(310)
                .build();

        ((OptionsListAccessor)(Object)optionsList).addBig(button);
    }
}
