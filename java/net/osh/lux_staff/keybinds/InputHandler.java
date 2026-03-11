package net.osh.lux_staff.keybinds;

import com.google.common.graph.Network;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.osh.lux_staff.LUXSTAFF;
import net.osh.lux_staff.item.lux_staff.LuxStaff;
import net.osh.lux_staff.item.lux_staff.ModWeapon;
import net.osh.lux_staff.skills.Q.LightBinding;
import net.osh.lux_staff.skills.zSkillLogic.SkillPacket;
import net.osh.lux_staff.skills.zSkillLogic.SkillType;

@EventBusSubscriber(
        modid = LUXSTAFF.MODID,
        value = net.neoforged.api.distmarker.Dist.CLIENT
)

public class InputHandler {

    private static boolean isHoldingLuxStaff(Minecraft mc) {
        if (mc.player == null) return false;

        return mc.player.getMainHandItem().getItem() instanceof LuxStaff
            || mc.player.getOffhandItem().getItem() instanceof LuxStaff;
    }

    @SubscribeEvent
        public static void InputHandler(InputEvent.Key event) {
            Minecraft mc = Minecraft.getInstance();

            if (mc.player == null) return;


            if (Keybind.LIGHT_BINDING.consumeClick()) {
                if (isHoldingLuxStaff(mc)) {
                    onCastQ(mc);
                }
            }

            if (Keybind.PRISMATIC_BARRIER.consumeClick()) {
                if (isHoldingLuxStaff(mc)){
                    onCastW(mc);
                }
            }

            if (Keybind.LUCENT_SINGULARITY.consumeClick()) {
                if (isHoldingLuxStaff(mc)) {
                    onCastE(mc);
                }
            }

            if (Keybind.FINAL_SPARK.consumeClick()) {
                if (isHoldingLuxStaff(mc)) {
                    onCastR(mc);
                }
            }
        }

        private static void onCastQ(Minecraft mc) {
            if (mc.player == null || mc.getConnection() == null) return;
            mc.getConnection().send(new SkillPacket(SkillType.Q));
        }

        private static void onCastW(Minecraft mc) {
            if (mc.player == null || mc.getConnection() == null) return;
            mc.getConnection().send(new SkillPacket(SkillType.W));
        }

        private static void onCastE(Minecraft mc) {
            if (mc.player == null || mc.getConnection() == null) return;
            mc.getConnection().send(new SkillPacket(SkillType.E));
        }

        private static void onCastR(Minecraft mc) {
            if (mc.player == null || mc.getConnection() == null) return;
            mc.getConnection().send(new SkillPacket(SkillType.R));
        }
}
