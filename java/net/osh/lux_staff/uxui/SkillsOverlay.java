package net.osh.lux_staff.uxui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.osh.lux_staff.LUXSTAFF;
import net.osh.lux_staff.attachment.ModAttachment;
import net.osh.lux_staff.cooldown.CooldownKeys;
import net.osh.lux_staff.item.lux_staff.LuxStaff;
import net.osh.lux_staff.item.lux_staff.tiers.StaffTier;
import net.osh.lux_staff.mana.PlayerMana;

public class SkillsOverlay {
    private static final ResourceLocation LB = ResourceLocation.fromNamespaceAndPath(LUXSTAFF.MODID, "textures/gui/light_binding.png");
    private static final ResourceLocation PB = ResourceLocation.fromNamespaceAndPath(LUXSTAFF.MODID, "textures/gui/prismatic_barrier.png");
    private static final ResourceLocation LS = ResourceLocation.fromNamespaceAndPath(LUXSTAFF.MODID, "textures/gui/lucent_singularity.png");
    private static final ResourceLocation LS_D = ResourceLocation.fromNamespaceAndPath(LUXSTAFF.MODID, "textures/gui/lucent_singularity_detonate.png");
    private static final ResourceLocation FS = ResourceLocation.fromNamespaceAndPath(LUXSTAFF.MODID, "textures/gui/final_spark.png");

    private static final ResourceLocation CooldownOvl = ResourceLocation.fromNamespaceAndPath(LUXSTAFF.MODID, "textures/gui/cooldown.png");
    private static final ResourceLocation NO_MANA_Ovl = ResourceLocation.fromNamespaceAndPath(LUXSTAFF.MODID, "textures/gui/not_enough_mana.png");

    private static boolean isHoldingLuxStaff(Minecraft mc) {
        if (mc.player == null) return false;

        return mc.player.getMainHandItem().getItem() instanceof LuxStaff
                || mc.player.getOffhandItem().getItem() instanceof LuxStaff;
    }

    private static void drawSkill1(GuiGraphics gui, int x, int y, int size) {
        gui.blit(LB, x - size / 2, y - size / 2, 0, 0, size, size, size, size);

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        int remaining = player.getData(ModAttachment.Q_COOLDOWN.get());
        int max = 20 * 10; // 5 seconds
        float percent = remaining / (float) max;

        if (remaining > 0) {
            int full = 23;
            int visibleHeight = (int)(full * percent);

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            gui.blit(CooldownOvl, x - 23 / 2, y - 23 / 2, 0, 0, 23, visibleHeight, 23, 23);
            RenderSystem.disableBlend();
        }
        int mana = 50;

        if (PlayerMana.get(player) < mana) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            gui.blit(NO_MANA_Ovl, x - 23 / 2, y - 23 / 2, 0, 0, 23, 23, 23, 23);
            RenderSystem.disableBlend();
        }
    }

    private static void drawSkill2(GuiGraphics gui, int x, int y, int size) {
        gui.blit(PB, x - size / 2, y - size / 2, 0, 0, size, size, size, size);

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof LuxStaff staff)) return;
        StaffTier tier = staff.getTier();

        int remaining = player.getData(ModAttachment.W_COOLDOWN.get());
        int max = 20 * tier.Wcooldown(); // 5 seconds
        float percent = remaining / (float) max;

        if (remaining > 0) {
            int full = 23;
            int visibleHeight = (int)(full * percent);

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            gui.blit(CooldownOvl, x - 23 / 2, y - 23 / 2, 0, 0, 23, visibleHeight, 23, 23);
            RenderSystem.disableBlend();
        }
        int mana = tier.WmanaCost();

        if (PlayerMana.get(player) < mana) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            gui.blit(NO_MANA_Ovl, x - 23 / 2, y - 23 / 2, 0, 0, 23, 23, 23, 23);
            RenderSystem.disableBlend();
        }
    }

    private static void drawSkill3(GuiGraphics gui, int x, int y, int size) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        boolean active = player.getData(ModAttachment.ACTIVE_SINGULARITY.get());
        if (!active) {
            gui.blit(LS, x - size / 2, y - size / 2, 0, 0, size, size, size, size);
        } else {
            gui.blit(LS_D, x - size / 2, y - size / 2, 0, 0, size, size, size, size );
        }

        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof LuxStaff staff)) return;
        StaffTier tier = staff.getTier();

        int remaining = player.getData(ModAttachment.E_COOLDOWN.get());
        int max = 20 * tier.Ecooldown(); // 5 seconds
        float percent = remaining / (float) max;

        if (remaining > 0) {

            int full = 23;
            int visibleHeight = (int)(full * percent);

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            gui.blit(CooldownOvl, x - 23 / 2, y - 23 / 2, 0, 0, 23, visibleHeight, 23, 23);
            RenderSystem.disableBlend();
        }
        int mana = tier.EmanaCost();

        if (PlayerMana.get(player) < mana) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            gui.blit(NO_MANA_Ovl, x - 23 / 2, y - 23 / 2, 0, 0, 23, 23, 23, 23);
            RenderSystem.disableBlend();
        }
    }

    private static void drawSkill4(GuiGraphics gui, int x, int y, int size) {
        gui.blit(FS, x - size / 2, y - size / 2, 0, 0, size, size, size, size);

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof LuxStaff staff)) return;
        StaffTier tier = staff.getTier();

        int remaining = player.getData(ModAttachment.R_COOLDOWN.get());
        int max = 20 * tier.Rcooldown(); // 5 seconds
        float percent = remaining / (float) max;

        if (remaining > 0) {
            int full = 23;
            int visibleHeight = (int)(full * percent);

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            gui.blit(CooldownOvl, x - 23 / 2, y - 23 / 2, 0, 0, 23, visibleHeight, 23, 23);
            RenderSystem.disableBlend();
        }
        int mana = 100;

        if (PlayerMana.get(player) < mana) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            gui.blit(NO_MANA_Ovl, x - 23 / 2, y - 23 / 2, 0, 0, 23, 23, 23, 23);
            RenderSystem.disableBlend();
        }
    }

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null) return;

        if (!(isHoldingLuxStaff(mc))) return;

        GuiGraphics gui = event.getGuiGraphics();
        renderSkillSlot(gui, mc);
    }

    private static void renderSkillSlot(GuiGraphics gui, Minecraft mc) {

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        // Hotbar baseline
        int hotbarY = screenHeight - 22;
        int hotbarRightX = (screenWidth / 2) + 91;

        int size = 15;
        int offset = 14 ;

        int baseX = hotbarRightX + 33; // slightly to the right
        int baseY = hotbarY - 7;

        drawSkill1(gui, baseX, baseY - offset, size);
        // Right
        drawSkill3(gui, baseX + offset, baseY, size);
        // Bottom
        drawSkill4(gui, baseX, baseY + offset, size);
        // Left
        drawSkill2(gui, baseX - offset, baseY, size);
    }
}
