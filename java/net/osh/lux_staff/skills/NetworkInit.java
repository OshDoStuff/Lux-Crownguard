package net.osh.lux_staff.skills;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.osh.lux_staff.LUXSTAFF;
import net.osh.lux_staff.skills.zSkillLogic.Handler;
import net.osh.lux_staff.skills.zSkillLogic.SkillPacket;

@EventBusSubscriber(modid = LUXSTAFF.MODID)
public class NetworkInit {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar("lux_staff")
                .playToServer(
                        SkillPacket.TYPE,
                        SkillPacket.STREAM_CODEC,
                        Handler::handle
                );
    }
}
