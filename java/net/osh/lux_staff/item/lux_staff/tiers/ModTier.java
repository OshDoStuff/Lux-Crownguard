package net.osh.lux_staff.item.lux_staff.tiers;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.osh.lux_staff.LUXSTAFF;

public class ModTier {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, LUXSTAFF.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<TierComponent>>
            STAFF_TIER = DATA_COMPONENT.register("staff_tier",
            () -> DataComponentType.<TierComponent>builder().persistent(TierComponent.CODEC).build()
    );

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT.register(eventBus);
    }
}
