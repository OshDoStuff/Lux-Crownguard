package net.osh.lux_staff.skills.E.Singularity;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.osh.lux_staff.cooldown.CooldownKeys;
import net.osh.lux_staff.datagen.ModDamageTypes;
import net.osh.lux_staff.effects.ModEffect;
import net.osh.lux_staff.item.lux_staff.tiers.StaffTier;
import net.osh.lux_staff.attachment.ModAttachment;
import net.osh.lux_staff.skills.ModEntities;

import java.util.List;

public class SingularityOrb extends Projectile {
    private static final int MAX_LIFE = 20*5;
    private AreaEffectCloud Cloud;

    public SingularityOrb(EntityType<?> entityType, Level level) {
        super(ModEntities.ORB.get(), level);
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide) {

            if (Cloud == null || !Cloud.isAlive()) {
                spawnCloud();
            }
            Cloud.setPos(getX(), getY() + 0.3 , getZ());

            if (this.tickCount >= MAX_LIFE) {
                explode();
            }
        }
    }

    private void spawnCloud() {
        Cloud = new AreaEffectCloud(level(), getX(), getY(), getZ());
        int slow = tier.slowBonus();

        Cloud.setRadius(3.5F);
        Cloud.setDuration(MAX_LIFE); // match orb lifespan
        Cloud.setWaitTime(0);

        Cloud.addEffect(new MobEffectInstance(
                MobEffects.MOVEMENT_SLOWDOWN,
                40,
                slow
        ));

        Cloud.addEffect(new MobEffectInstance(
                MobEffects.GLOWING,
                40,
                0
        ));

        Cloud.setParticle(ParticleTypes.ENCHANTED_HIT);

        level().addFreshEntity(Cloud);
    }

    public void explode() {
        double rad = 3D;
        int amp = this.tier.effectAmplifier();
        float dmgP = this.tier.DamageBonus();

        List<LivingEntity> targets = level().getEntitiesOfClass(
                LivingEntity.class,
                this.getBoundingBox().inflate(rad)
        );
        if (level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.FLASH,
                    getX(), getY(), getZ(), 20,
                    1, 1, 1,
                    0);
        }
        if (level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.SONIC_BOOM,
                    getX(), getY(), getZ(), 10,
                    0, 1, 0,
                    0);
        }

        for (LivingEntity target : targets) {
            if (target == this.getOwner()) continue;
            double dist = this.distanceTo(target);
            float damagePlus = dmgP;
            if (dist <= rad) {
                float damage = 3F + damagePlus;
                target.hurt(
                        damageSources().source(ModDamageTypes.ILLUMINATION),
                        damage
                );
                target.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN,
                        60,
                        0
                ));
                target.addEffect(new MobEffectInstance(
                        ModEffect.ILLUMINATION,
                        20 * 7, amp
                ));
            }
        }
        int cooldown = tier.Ecooldown();

        if (!level().isClientSide && this.getOwner() instanceof Player player) {
            player.setData(ModAttachment.ACTIVE_SINGULARITY.get(), false);
        }
        this.getOwner().setData(ModAttachment.E_COOLDOWN.get(), 20 * cooldown);
        this.discard();
        if (Cloud.isAlive() && Cloud != null) {
            Cloud.discard();
        }
    }

    private StaffTier tier = StaffTier.Classic;

    public void setTier(StaffTier tier) {
        this.tier = tier;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putString("Tier", tier.name());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        tier = StaffTier.valueOf(tag.getString("Tier"));
    }
}
