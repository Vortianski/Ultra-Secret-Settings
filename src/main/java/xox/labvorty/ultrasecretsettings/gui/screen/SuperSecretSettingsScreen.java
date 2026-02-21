package xox.labvorty.ultrasecretsettings.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import xox.labvorty.ultrasecretsettings.accessors.OptionsListAccessor;
import xox.labvorty.ultrasecretsettings.data.SuperSecretSettingsData;

public class SuperSecretSettingsScreen extends OptionsSubScreen {
    public HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
    private StringWidget stringWidget;
    private Button prevShader;
    private Button nextShader;
    private Button shadersActive;
    private Button apply;

    public SuperSecretSettingsScreen(Screen lastScreen) {
        super(lastScreen, Minecraft.getInstance().options, Component.literal("Super Secret Settings"));
    }

    @Override
    protected void addOptions() {
        if (this.minecraft == null) return;

        boolean ingame = false;
        Level level = this.minecraft.level;
        if (level != null) {
            ingame = true;
        }

        stringWidget = new StringWidget(
                ingame ? Component.translatable("ultrasecretsettings.current_shader", SuperSecretSettingsData.getTranslatedShaderName(SuperSecretSettingsData.unappliedShader)) : Component.translatable("ultrasecretsettings.current_shader", "Disabled"),
                this.font
        );
        stringWidget.setWidth(310);
        stringWidget.setHeight(25);

        prevShader = Button.builder(
                Component.translatable("ultrasecretsettings.previous"),
                btn -> {
                    SuperSecretSettingsData.shadersBackward();
                    updateData();
                }
        ).build();

        nextShader = Button.builder(
                Component.translatable("ultrasecretsettings.next"),
                btn -> {
                    SuperSecretSettingsData.shadersForward();
                    updateData();
                }
        ).build();

        shadersActive = Button.builder(
                Component.translatable("ultrasecretsettings.active", SuperSecretSettingsData.shadersActive),
                btn -> {
                    SuperSecretSettingsData.toggleShaders();
                    updateData();
                }
        ).width(310).build();

        apply = Button.builder(
                Component.translatable("ultrasecretsettings.apply"),
                btn -> {
                    SuperSecretSettingsData.apply();
                    updateData();
                }
        ).width(310).build();

        prevShader.active = ingame;
        nextShader.active = ingame;
        shadersActive.active = ingame;
        apply.active = ingame;

        if (!ingame) {
            prevShader.setTooltip(
                    Tooltip.create(
                            Component.translatable("ultrasecretsettings.ingame")
                    )
            );
            nextShader.setTooltip(
                    Tooltip.create(
                            Component.translatable("ultrasecretsettings.ingame")
                    )
            );
            shadersActive.setTooltip(
                    Tooltip.create(
                            Component.translatable("ultrasecretsettings.ingame")
                    )
            );
            apply.setTooltip(
                    Tooltip.create(
                            Component.translatable("ultrasecretsettings.ingame")
                    )
            );
        }

        ((OptionsListAccessor) (Object) this.list).addBig(stringWidget);
        ((OptionsListAccessor) (Object) this.list).addBig(shadersActive);
        this.list.addSmall(prevShader, nextShader);
        ((OptionsListAccessor) (Object) this.list).addBig(apply);
    }

    private void updateData() {
        if (this.minecraft == null) return;

        boolean ingame = false;
        Level level = this.minecraft.level;
        if (level != null) {
            ingame = true;
        }

        stringWidget.setMessage(ingame ? Component.translatable("ultrasecretsettings.current_shader", SuperSecretSettingsData.getTranslatedShaderName(SuperSecretSettingsData.unappliedShader)) : Component.translatable("ultrasecretsettings.current_shader", "Disabled"));
        shadersActive.setMessage(Component.literal("Active: " + SuperSecretSettingsData.shadersActive));
    }

    @Override
    public void onClose() {
        super.onClose();
        SuperSecretSettingsData.unappliedShader = SuperSecretSettingsData.currentShader;
    }
}
