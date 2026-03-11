package net.osh.lux_staff.uxui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.osh.lux_staff.LUXSTAFF;
import net.osh.lux_staff.attachment.ModAttachment;
import net.osh.lux_staff.cooldown.CooldownKeys;
import net.osh.lux_staff.item.lux_staff.LuxStaff;

public class HUDoverlay {
    private static final ResourceLocation SkillSlot = ResourceLocation.fromNamespaceAndPath(LUXSTAFF.MODID, "textures/gui/skill_slot.png");
    private static final ResourceLocation SkillSlotREC = ResourceLocation.fromNamespaceAndPath(LUXSTAFF.MODID, "textures/gui/skill_slot_rec.png");

    private static boolean isHoldingLuxStaff(Minecraft mc) {
        if (mc.player == null) return false;

        return mc.player.getMainHandItem().getItem() instanceof LuxStaff
                || mc.player.getOffhandItem().getItem() instanceof LuxStaff;
    }

    private static void drawDiamond(GuiGraphics gui, int x, int y, int size) {
        gui.blit(
                SkillSlot, x - size / 2, y - size / 2, 0, 0, size, size, size, size
        );
    }

    private static void drawDiamondR(GuiGraphics gui, int x, int y, int size) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player != null){
            boolean active = player.getData(ModAttachment.ACTIVE_SINGULARITY.get());
            if (!active) {
                gui.blit(SkillSlot, x - size / 2, y - size / 2, 0, 0, size, size, size, size);
            } else {
                gui.blit(SkillSlotREC, x - size / 2, y - size / 2, 0, 0, size, size, size, size );
            }
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

        int size = 23;
        int offset = 14 ;

        int baseX = hotbarRightX + 33; // slightly to the right
        int baseY = hotbarY - 7;

        //Top
        drawDiamond(gui, baseX, baseY - offset, size);
        // Right
        drawDiamondR(gui, baseX + offset, baseY, size);
        // Bottom
        drawDiamond(gui, baseX, baseY + offset, size);
        // Left
        drawDiamond(gui, baseX - offset, baseY, size);
    }
}
