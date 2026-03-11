package net.osh.lux_staff.mana;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.osh.lux_staff.LUXSTAFF;
import net.osh.lux_staff.attachment.ModAttachment;

@EventBusSubscriber(modid = LUXSTAFF.MODID)
public class PlayerMana {
    public static int get(Player player) {
        return player.getData(ModAttachment.PLAYER_MANA.get());
    }
    public static int getMax(Player player) {
        return player.getData(ModAttachment.PLAYER_MAX_MANA.get());
    }

    public static boolean consume(Player player, int cost) {
        if (player.getAbilities().instabuild) {
            return true;
        }

        int current = get(player);

        if (get(player) < cost) return false;
        set(player, current - cost);

        if (player instanceof ServerPlayer serverPlayer) {
            long gameTime = serverPlayer.level().getGameTime();
            serverPlayer.syncData(ModAttachment.PLAYER_MANA.get());
        }
        return true;
    }

    public static void set(Player player, int val) {
        player.setData(
                ModAttachment.PLAYER_MANA.get(),
                Mth.clamp(val, 0, getMax(player))
        );
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        int mana = PlayerMana.get(player);
        int max = PlayerMana.getMax(player);

        if (mana >= max) return;

        long lastUse = player.getData(ModAttachment.SINCE_LAST_USED.get());
        long currentTime = player.level().getGameTime();

        int regenDelay = 20;       // 1 second
        int regenInterval = 20*5;    // every 10 ticks

        if (currentTime - lastUse < regenDelay) return;

        if (currentTime % regenInterval == 0) {
            PlayerMana.set(player, mana + 5); // regen 5 per tick interval
        }
    }
}
