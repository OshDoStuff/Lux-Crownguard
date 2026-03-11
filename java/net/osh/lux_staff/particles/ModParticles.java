package net.osh.lux_staff.particles;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.osh.lux_staff.LUXSTAFF;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, LUXSTAFF.MODID);

    //------------------------------------------------------------------------------------------------------------------

    public static final Supplier<SimpleParticleType> LUMINOUS_PARTICLE =
            PARTICLE.register("illumination_particle", () -> new SimpleParticleType(true));

    //------------------------------------------------------------------------------------------------------------------

    public static void register(IEventBus eventBus) {
        PARTICLE.register(eventBus);
    }
}
