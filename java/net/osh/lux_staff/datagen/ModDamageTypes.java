package net.osh.lux_staff.datagen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.osh.lux_staff.LUXSTAFF;

public interface ModDamageTypes {
    ResourceKey<DamageType> ILLUMINATION = register("illuminated");

    static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, LUXSTAFF.res(name));
    }

    static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(ILLUMINATION, new DamageType("illuminated", .1f));
    }
}
