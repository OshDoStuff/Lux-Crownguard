package net.osh.lux_staff.skills.zSkillLogic;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.osh.lux_staff.skills.E.LucentSingularity;
import net.osh.lux_staff.skills.Q.LightBinding;
import net.osh.lux_staff.skills.R.FinalSpark;
import net.osh.lux_staff.skills.W.PrismaticBarrier;

public final class Handler {

    public static void handle(SkillPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;

            switch (packet.skill()) {
            case Q -> LightBinding.cast(player);
            case W -> PrismaticBarrier.cast(player);
            case E -> LucentSingularity.cast(player);
            case R -> FinalSpark.cast(player);
            }
        });
    }
}
