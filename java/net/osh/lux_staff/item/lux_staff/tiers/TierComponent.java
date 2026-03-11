package net.osh.lux_staff.item.lux_staff.tiers;

import com.mojang.serialization.Codec;

public record TierComponent(StaffTier tier) {
    public static final Codec<TierComponent> CODEC =
            Codec.STRING.xmap(
                    s -> new TierComponent(StaffTier.valueOf(s)),
                    c -> c.tier().name()
            );
}
