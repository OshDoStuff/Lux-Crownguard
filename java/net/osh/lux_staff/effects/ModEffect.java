package net.osh.lux_staff.effects;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.osh.lux_staff.LUXSTAFF;
import net.osh.lux_staff.effects.Illumination.Illumination;
import net.osh.lux_staff.effects.Root.Root;

public class ModEffect {
    public static final DeferredRegister<MobEffect> MOB_EFFECT =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, LUXSTAFF.MODID);

//------------------------------------------------------------------------------------------------------------------

        public static final Holder<MobEffect> ILLUMINATION = MOB_EFFECT.register("illumination",
                () -> new Illumination(MobEffectCategory.HARMFUL, 0xFFF2A1)
        );

        public static final Holder<MobEffect> ROOT = MOB_EFFECT.register("root",
                () -> new Root(MobEffectCategory.HARMFUL, 0xEAAD27)
        );

//------------------------------------------------------------------------------------------------------------------

    public static void register(IEventBus eventBus) {
        MOB_EFFECT.register(eventBus);
    }
}
