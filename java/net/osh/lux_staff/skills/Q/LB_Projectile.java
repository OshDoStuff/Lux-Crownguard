package net.osh.lux_staff.skills.Q;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.osh.lux_staff.datagen.ModDamageTypes;
import net.osh.lux_staff.effects.ModEffect;
import net.osh.lux_staff.item.lux_staff.LuxStaff;
import net.osh.lux_staff.item.lux_staff.tiers.StaffTier;
import net.osh.lux_staff.skills.ModEntities;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LB_Projectile extends Projectile {

    public static final int LifeTime = 15;
    public static final int PierceCap = 1;

    private int life = 0;
    private int pierced = 0;
    private final Set<UUID> hit = new HashSet<>();
    //StaffTier staffTier = LuxStaff.getTier(stack);

    public LB_Projectile(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
        setNoGravity(true);
        setInvisible(false);
        this.setBoundingBox(this.getBoundingBox().inflate(5));
    }

    public LB_Projectile(Level level, LivingEntity owner) {
        super(ModEntities.LBP.get(), level);
        this.setOwner(owner);

        Vec3 look = owner.getLookAngle();
        Vec3 spawnPos = owner.position()
                .add(look.scale(0.8))
                .add(0, owner.getEyeHeight() - 0.3, 0);
        this.setPos(spawnPos.x, spawnPos.y, spawnPos.z);

        HitResult hit = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hit.getType() != HitResult.Type.MISS) this.onHit(hit);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        Entity owner = this.getOwner();
        float scaledDamage = 3 + tier.DamageBonus();
        int effectMultiplier = tier.effectAmplifier();

        if (hit.contains(target.getUUID())) return;
        hit.add(target.getUUID());

        if (target instanceof LivingEntity livingEntity && owner instanceof Player player) {

            float damage = scaledDamage;
            livingEntity.hurt(player.level().damageSources().source(
                    ModDamageTypes.ILLUMINATION, player
            ), damage);

            livingEntity.addEffect(new MobEffectInstance(
                  ModEffect.ROOT,
                    40,
                    0,
                    false,
                    true
            ));

            livingEntity.addEffect(new MobEffectInstance(
                    ModEffect.ILLUMINATION,
                    20 * 7,
                    effectMultiplier,
                    false,
                    true
            ));
        }

        pierced++;
        if (pierced > PierceCap) {
            discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        life++;

        if (this.level().isClientSide) {
            spawnTrailParticles();
        }

        HitResult hit = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hit.getType() != HitResult.Type.MISS) {
            this.onHit(hit);
        }

        Vec3 motion = this.getDeltaMovement();
        this.move(MoverType.SELF, motion);

        if (life > LifeTime) {
            this.discard();
        }
    }

    private void spawnTrailParticles() {
        Vec3 dir = getDeltaMovement();
        if (dir.lengthSqr() < 0.0001) {
            dir = Vec3.directionFromRotation(getXRot(), getYRot());
        }
        dir = dir.normalize();

        Vec3 right = dir.cross(new Vec3(0, 1, 0)).normalize();
        Vec3 up = right.cross(dir).normalize();

        for (int i = 0; i < 8; i++) {
            // distance behind the projectile
            double t = i / 10.0;
            double dist = t * 0.1;

            // cone widening (tight head, wide tail)
            double radius = t * 1;

            // random radial offset
            double angle = level().random.nextDouble() * Math.PI * 2;
            double r = level().random.nextDouble() * radius;

            double rx = Math.cos(angle) * r;
            double ry = Math.sin(angle) * r;

            Vec3 offset =
                    dir.scale(-dist)
                            .add(right.scale(rx))
                            .add(up.scale(ry));

            // slight backward motion for "burning" feel
            level().addParticle(
                    ParticleTypes.ELECTRIC_SPARK, // swap later
                    getX() + offset.x,
                    getY() + offset.y,
                    getZ() + offset.z,
                    -dir.x * 0.02,
                    -dir.y * 0.02,
                    -dir.z * 0.02
            );
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.discard();
    }

    public void shootFromRotation(LivingEntity shooter, float pitch, float yaw, float roll, float velocity, float inaccuracy) {
        Vec3 dir = Vec3.directionFromRotation(pitch, yaw);
        this.setDeltaMovement(dir.scale(velocity));
        this.setYRot(yaw);
        this.setXRot(pitch);
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
