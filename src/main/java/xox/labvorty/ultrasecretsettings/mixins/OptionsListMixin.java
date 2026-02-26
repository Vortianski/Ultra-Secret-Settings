package xox.labvorty.ultrasecretsettings.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import xox.labvorty.ultrasecretsettings.accessors.OptionsListAccessor;

import java.util.List;

@Mixin(OptionsList.class)
public abstract class OptionsListMixin extends ContainerObjectSelectionList<OptionsList.Entry> implements OptionsListAccessor {
    @Final
    @Shadow
    private OptionsSubScreen screen;

    public OptionsListMixin(Minecraft minecraft, int width, int height, int y, int itemHeight) {
        super(minecraft, width, height, y, itemHeight);
    }

    @Unique
    public void addBig(AbstractWidget option) {
        this.addEntry(OptionsList.Entry.big(List.of(option), this.screen));
    }
}