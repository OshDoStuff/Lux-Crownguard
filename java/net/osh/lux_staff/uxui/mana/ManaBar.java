package net.osh.lux_staff.uxui.mana;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.osh.lux_staff.item.lux_staff.LuxStaff;
import net.osh.lux_staff.mana.PlayerMana;

public class ManaBar {
    private static final ResourceLocation BLUE_BG =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/sprites/boss_bar/blue_background.png");
    private static final ResourceLocation BLUE_FILL =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/sprites/boss_bar/blue_progress.png");
    private static final ResourceLocation NOTCHES_BG =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/sprites/boss_bar/notched_20_background.png");
    private static final ResourceLocation NOTCHES_FILL =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/sprites/boss_bar/notched_20_progress.png");

    public static void renderManaBar(GuiGraphics graphics, Minecraft mc) {
        Player player = mc.player;
        if (player == null) return;

        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof LuxStaff)) return;

        int mana = PlayerMana.get(player);
        int max = PlayerMana.getMax(player);
        float progress = mana / (float) max;

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int barWidth = 182;
        int barHeight = 5;
        int x = (screenWidth / 2) - (barWidth / 2);
        int y = screenHeight - 29;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        // draw background
        graphics.blit(BLUE_BG, x, y, 0, 0, barWidth, barHeight, barWidth, barHeight);
        graphics.blit(NOTCHES_BG, x, y, 0, 0, barWidth, barHeight, barWidth, barHeight);

        // draw fill
        int filled = (int) (barWidth * progress);
        graphics.blit(BLUE_FILL, x, y, 0, 0, filled, barHeight, barWidth, barHeight);
        graphics.blit(NOTCHES_FILL, x, y, 0, 0, filled, barHeight, barWidth, barHeight);

        RenderSystem.disableBlend();
        // optional text
        String text = String.valueOf(mana);
        graphics.drawCenteredString(mc.font, text, screenWidth / 2, y - 8, 0x00B7EC);
    }
}
