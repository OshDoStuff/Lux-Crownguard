package net.osh.lux_staff.skills;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.osh.lux_staff.LUXSTAFF;
import net.osh.lux_staff.skills.E.Projectile.LS_Projectile;
import net.osh.lux_staff.skills.E.Singularity.SingularityOrb;
import net.osh.lux_staff.skills.Q.LB_Projectile;
import net.osh.lux_staff.skills.R.FS_Entity;
import net.osh.lux_staff.skills.W.ThrownStaff;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, LUXSTAFF.MODID);

    //------------------------------------------------------------------------------------------------------------------
    public static final Supplier<EntityType<LB_Projectile>> LBP =
            ENTITIES.register("light_binding_projectile",
                    () -> EntityType.Builder.<LB_Projectile>of(LB_Projectile::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(8)
                            .updateInterval(2)
                            .build("light_binding_projectile"));

    public static final Supplier<EntityType<LS_Projectile>> LSP =
            ENTITIES.register("lucent_singularity_projectile",
                    () -> EntityType.Builder.<LS_Projectile>of(LS_Projectile::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(8)
                            .updateInterval(2)
                            .build("lucent_singularity_projectile"));

    public static final Supplier<EntityType<ThrownStaff>> THROWN_STAFF =
            ENTITIES.register("thrown_staff",
                    () -> EntityType.Builder.<ThrownStaff>of(ThrownStaff::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(8)
                            .updateInterval(2)
                            .build("thrown_staff"));

    public static final Supplier<EntityType<SingularityOrb>> ORB =
            ENTITIES.register("singularity_orb",
                    () -> EntityType.Builder.<SingularityOrb>of((entityType, level)
                                    -> new SingularityOrb(entityType, level), MobCategory.MISC)
                            .sized(0.5f, 0.5f).clientTrackingRange(8).updateInterval(2).build("singularity_orb"));

    public static final Supplier<EntityType<FS_Entity>> LASER =
            ENTITIES.register("final_spark",
                    () -> EntityType.Builder.<FS_Entity>of((entityType, level)
                                    -> new FS_Entity(entityType, level), MobCategory.MISC)
                            .sized(3f, 3f).clientTrackingRange(8).updateInterval(2).build("final_spark"));
    //------------------------------------------------------------------------------------------------------------------

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
