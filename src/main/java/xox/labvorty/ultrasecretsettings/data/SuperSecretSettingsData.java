package xox.labvorty.ultrasecretsettings.data;

import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.stream.Collectors;

public class SuperSecretSettingsData {
    public static String currentShader = "ultrasecretsettings:shaders/post/pencil.json";
    public static List<String> shaders = List.of(
            "ultrasecretsettings:shaders/post/pencil.json",
            "ultrasecretsettings:shaders/post/wobble.json",
            "ultrasecretsettings:shaders/post/sobel.json",
            "ultrasecretsettings:shaders/post/scan_pincushion.json",
            "ultrasecretsettings:shaders/post/flip.json",
            "ultrasecretsettings:shaders/post/notch.json",
            "ultrasecretsettings:shaders/post/antialias.json",
            "ultrasecretsettings:shaders/post/terminal.json",
            "ultrasecretsettings:shaders/post/blobs.json",
            "ultrasecretsettings:shaders/post/blobs2.json",
            "ultrasecretsettings:shaders/post/blur.json",
            "ultrasecretsettings:shaders/post/art.json",
            "ultrasecretsettings:shaders/post/bumpy.json",
            "ultrasecretsettings:shaders/post/saturate.json",
            "ultrasecretsettings:shaders/post/desaturate.json",
            "ultrasecretsettings:shaders/post/fxaa.json",
            "ultrasecretsettings:shaders/post/deconverge.json",
            "ultrasecretsettings:shaders/post/kaleidoscope.json",
            "ultrasecretsettings:shaders/post/protanopia.json",
            "ultrasecretsettings:shaders/post/deuteranopia.json",
            "ultrasecretsettings:shaders/post/tritanopia.json",
            "ultrasecretsettings:shaders/post/crystal.json",
            "ultrasecretsettings:shaders/post/harsh_dither.json",
            "minecraft:shaders/post/creeper.json",
            "minecraft:shaders/post/invert.json",
            "minecraft:shaders/post/spider.json",
            "ultrasecretsettings:shaders/post/8bit.json",
            "ultrasecretsettings:shaders/post/phosphor.json",
            "ultrasecretsettings:shaders/post/3d.json",
            "ultrasecretsettings:shaders/post/vhs.json"
    );
    public static String unappliedShader = "ultrasecretsettings:shaders/post/pencil.json";
    public static boolean shadersActive = false;

    public static void toggleShaders() {
        shadersActive = !shadersActive;
    }

    public static void shadersForward() {
        int index = shaders.indexOf(unappliedShader);
        if (index < shaders.size() - 1) {
            unappliedShader = shaders.get(index + 1);
        } else {
            unappliedShader = shaders.get(0);
        }
    }

    public static void shadersBackward() {
        int index = shaders.indexOf(unappliedShader);
        if (index > 0) {
            unappliedShader = shaders.get(index - 1);
        } else {
            unappliedShader = shaders.get(shaders.size() - 1);
        }
    }

    public static void apply() {
        currentShader = unappliedShader;
    }

    public static Component getTranslatedShaderName(String shaderPath) {
        String filename = shaderPath.substring(shaderPath.lastIndexOf('/') + 1);
        String nameWithoutExt = filename.replace(".json", "");
        return Component.translatable("ultrasecretsettings." + nameWithoutExt);
    }
}
