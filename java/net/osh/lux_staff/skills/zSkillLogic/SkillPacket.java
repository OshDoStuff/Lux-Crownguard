package net.osh.lux_staff.skills.zSkillLogic;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SkillPacket(SkillType skill) implements CustomPacketPayload {

    public static final Type<SkillPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("lux_staff", "lux_skills"));

    public static final StreamCodec<FriendlyByteBuf, SkillType> SKILL_CODEC =
            StreamCodec.of(
                    (buf, skill) -> buf.writeVarInt(skill.ordinal()),
                    buf -> SkillType.values()[buf.readVarInt()]
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, SkillPacket> STREAM_CODEC =
            StreamCodec.of(
                    SkillPacket::encode,
                    SkillPacket::decode
            );

    private static void encode(RegistryFriendlyByteBuf buf, SkillPacket packet) {
        buf.writeEnum(packet.skill());
    }

    private static SkillPacket decode(RegistryFriendlyByteBuf buf) {
        return new SkillPacket(buf.readEnum(SkillType.class));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
