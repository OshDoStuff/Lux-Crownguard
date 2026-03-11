package net.osh.lux_staff.uxui.mana;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.osh.lux_staff.item.lux_staff.LuxStaff;

import static net.osh.lux_staff.uxui.mana.ManaBar.renderManaBar;

public class ExpBarRemove {

    private static boolean isHoldingLuxStaff(Minecraft mc) {
        if (mc.player == null) return false;

        return mc.player.getMainHandItem().getItem() instanceof LuxStaff
                || mc.player.getOffhandItem().getItem() instanceof LuxStaff;
    }

    @SubscribeEvent
    public static void onRenderGui(RenderGuiLayerEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || mc.options.hideGui) return;

        if (!(isHoldingLuxStaff(mc))) return;

        String name = event.getName().toString();
        if (name.equals("minecraft:experience_bar") || name.equals("minecraft:experience_level")) {
            if (name.equals("minecraft:experience_bar")) {
                renderManaBar(event.getGuiGraphics(), mc);
            }
            event.setCanceled(true);
        }
    }
}
