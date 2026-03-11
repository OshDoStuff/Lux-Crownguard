package net.osh.lux_staff.cooldown;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.osh.lux_staff.LUXSTAFF;
import net.osh.lux_staff.attachment.ModAttachment;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = LUXSTAFF.MODID)
public class PlayerCooldown {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        tick(player, ModAttachment.Q_COOLDOWN.get());
        tick(player, ModAttachment.W_COOLDOWN.get());
        tick(player, ModAttachment.E_COOLDOWN.get());
        tick(player, ModAttachment.R_COOLDOWN.get());
    }

    private static void tick(ServerPlayer player, AttachmentType<Integer> type) {
        int cd = player.getData(type);
        if (cd > 0) {
            player.setData(type, cd - 1);
        }
    }
}
