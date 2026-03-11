package net.osh.lux_staff.skills.R;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.osh.lux_staff.effects.ModEffect;
import net.osh.lux_staff.item.lux_staff.LuxStaff;
import net.osh.lux_staff.item.lux_staff.tiers.StaffTier;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FS_Entity extends Entity {
    private int life;

    private ServerPlayer caster;
    private Vec3 start;
    private Vec3 dir;

    public FS_Entity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public void setup(ServerPlayer player) {
        this.caster = player;
        this.start = player.getEyePosition();
        this.dir = player.getLookAngle().normalize();
        this.setPos(player.getEyePosition());
    }

    @Override
    public void tick() {
        super.tick();

        if(level().isClientSide) return;

        life++;

        if(life < 20) {
            spawnWarningBeam();
        }

        if(life == 20) {
            fireLaser();
        }

        if(life > 22) {
            discard();
        }
    }

    private void fireLaser() {
        double range = 500;
        double step = 0.5;

        ServerLevel level = (ServerLevel)this.level();
        if (caster == null) return;

        Set<LivingEntity> hitEntities = new HashSet<>();

        ItemStack stack = caster.getMainHandItem();
        if (!(stack.getItem() instanceof LuxStaff staff)) return;
        StaffTier tier = staff.getTier();


        int effectMultiplier = tier.effectAmplifier();

        for(double d = 0; d <= range; d += step) {
            Vec3 point = start.add(dir.scale(d));
            ((ServerLevel) level).sendParticles(
                    ParticleTypes.FLASH, point.x, point.y, point.z,
                    1, 0, 0, 0, 0);
            ((ServerLevel) level).sendParticles(
                    ParticleTypes.END_ROD, point.x, point.y, point.z,
                    1, 0.2, 0.2, 0.2, 0);
            ((ServerLevel) level).sendParticles(
                    ParticleTypes.ENCHANTED_HIT, point.x, point.y, point.z,
                    1, 0.2, 0.2, 0.2, 0);

            AABB box = new AABB(
                    point.x - 1, point.y - 1, point.z - 1,
                    point.x + 1, point.y + 1, point.z + 1
            );

            List<LivingEntity> entities =
                    level.getEntitiesOfClass(LivingEntity.class, box);
            for (LivingEntity target : entities) {
                if (hitEntities.add(target)) {
                    float damage = tier.UltDamage();

                    MobEffectInstance existing = target.getEffect(ModEffect.ILLUMINATION);

                    if (existing != null) {
                        int stacks = existing.getAmplifier() + 1;
                        damage += stacks * 3;
                    }

                    target.hurt(caster.damageSources().magic(), damage);

                    if (target == caster) continue;
                    target.addEffect(new MobEffectInstance(
                            ModEffect.ILLUMINATION,
                            20 * 7,
                            effectMultiplier,
                            false,
                            true
                    ));
                }
            }
        }
    }

    private void spawnWarningBeam() {
        double range = 500;
        double step = 0.5;

        ServerLevel level = (ServerLevel)this.level();
        for(double d = 0; d <= range; d += step) {
            Vec3 point = start.add(dir.scale(d));
            level.sendParticles(
                    new DustParticleOptions(new Vector3f(1f, 0, 0), 0.5f),
                    point.x, point.y, point.z,
                    1, 0, 0, 0, 0);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {

    }
}
