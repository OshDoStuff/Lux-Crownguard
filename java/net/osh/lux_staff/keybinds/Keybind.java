package net.osh.lux_staff.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.osh.lux_staff.LUXSTAFF;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(
        modid = LUXSTAFF.MODID,
        value = net.neoforged.api.distmarker.Dist.CLIENT
)

public class Keybind{
    public static final String CATEGORY = "key.category.lux_staff";

    public static final KeyMapping LIGHT_BINDING = new KeyMapping(
            "key.lux_staff.light_binding",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            CATEGORY
    );

    public static final KeyMapping PRISMATIC_BARRIER = new KeyMapping(
            "key.lux_staff.prismatic_barrier",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            CATEGORY
    );

    public static final KeyMapping LUCENT_SINGULARITY = new KeyMapping(
            "key.lux_staff.lucent_singularity",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            CATEGORY
    );

    public static final KeyMapping FINAL_SPARK = new KeyMapping(
            "key.lux_staff.final_spark",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            CATEGORY
    );

    @SubscribeEvent
    public static void registerKeybind(RegisterKeyMappingsEvent event) {
        event.register(Keybind.LIGHT_BINDING);
        event.register(Keybind.PRISMATIC_BARRIER);
        event.register(Keybind.LUCENT_SINGULARITY);
        event.register(Keybind.FINAL_SPARK);
    }

}
