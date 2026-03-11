package net.osh.lux_staff.attachment;

import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.osh.lux_staff.LUXSTAFF;
import net.osh.lux_staff.cooldown.PlayerCooldown;

import java.util.function.Supplier;

public class ModAttachment {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, LUXSTAFF.MODID);

    //------------------------------------------------------------------------------------------------------------------

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> ACTIVE_SINGULARITY =
            ATTACHMENT.register("active_singularity", () -> AttachmentType.<Boolean>builder(() -> false)
                    .serialize(Codec.BOOL)
                    .sync(ByteBufCodecs.BOOL).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> PLAYER_MANA =
            ATTACHMENT.register("player_mana",
                    () -> AttachmentType.<Integer>builder(() -> 900)
                            .serialize(Codec.INT)
                            .sync(ByteBufCodecs.INT).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> PLAYER_MAX_MANA =
            ATTACHMENT.register("player_max_mana",
                    () -> AttachmentType.<Integer>builder(() -> 900)
                            .serialize(Codec.INT)
                            .copyOnDeath().build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Long>> SINCE_LAST_USED =
            ATTACHMENT.register("since_last_use",
                    () -> AttachmentType.<Long>builder(() -> 0L)
                            .serialize(Codec.LONG).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> Q_COOLDOWN =
            ATTACHMENT.register("q_cooldown",
                    () -> AttachmentType.<Integer>builder(() -> 0)
                            .serialize(Codec.INT)
                            .sync(ByteBufCodecs.INT).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> W_COOLDOWN =
            ATTACHMENT.register("w_cooldown",
                    () -> AttachmentType.<Integer>builder(() -> 0)
                            .serialize(Codec.INT)
                            .sync(ByteBufCodecs.INT).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> E_COOLDOWN =
            ATTACHMENT.register("e_cooldown",
                    () -> AttachmentType.<Integer>builder(() -> 0)
                            .serialize(Codec.INT)
                            .sync(ByteBufCodecs.INT).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> R_COOLDOWN =
            ATTACHMENT.register("r_cooldown",
                    () -> AttachmentType.<Integer>builder(() -> 0)
                            .serialize(Codec.INT)
                            .sync(ByteBufCodecs.INT).build());

    //------------------------------------------------------------------------------------------------------------------
}
