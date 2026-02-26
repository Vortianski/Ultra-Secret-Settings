package xox.labvorty.ultrasecretsettings.mixins;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import xox.labvorty.ultrasecretsettings.gui.screen.SuperSecretSettingsScreen;

import java.util.function.Supplier;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin {
    @Shadow
    protected abstract Button openScreenButton(Component name, Supplier<Screen> screenSupplier);

    @Inject(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;",
                    ordinal = 9,
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void ultrasecretsettings$init(CallbackInfo ci, LinearLayout linearlayout, LinearLayout linearlayout1, GridLayout gridlayout, GridLayout.RowHelper gridlayout$rowhelper, Button button) {
        OptionsScreen optionsScreen = (OptionsScreen)(Object)this;

        gridlayout$rowhelper.addChild(this.openScreenButton(Component.literal("Super Secret Settings"), () -> new SuperSecretSettingsScreen(optionsScreen)));
    }
}
