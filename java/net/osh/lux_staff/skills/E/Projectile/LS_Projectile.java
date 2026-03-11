package net.osh.lux_staff.skills.E.Projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.osh.lux_staff.item.lux_staff.tiers.StaffTier;
import net.osh.lux_staff.skills.E.Singularity.SingularityOrb;
import net.osh.lux_staff.skills.ModEntities;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LS_Projectile extends Projectile {

    public static final int LifeTime = 7;
    private int life = 0;

    public LS_Projectile(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
        setNoGravity(true);
        setInvisible(false);
        this.setBoundingBox(this.getBoundingBox().inflate(5));
    }

    public LS_Projectile(Level level, LivingEntity owner) {
        super(ModEntities.LSP.get(), level);
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
            spawnSingularity();
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

            level().addParticle(
                    ParticleTypes.WAX_ON, // swap later
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

        if (!this.level().isClientSide) {
            spawnSingularity();
            this.discard();
        }
    }

    private void spawnSingularity() {
        Vec3 groundPos = findGroundPosition();
        SingularityOrb singularity =
                new SingularityOrb(ModEntities.ORB.get(), this.level());

        singularity.setPos(
                groundPos.x,
                groundPos.y,
                groundPos.z
        );

        singularity.setOwner(this.getOwner());
        singularity.setTier(this.tier);

        this.level().addFreshEntity(singularity);
    }

    public void shootFromRotation(LivingEntity shooter, float pitch, float yaw, float roll, float velocity, float inaccuracy) {
        Vec3 dir = Vec3.directionFromRotation(pitch, yaw);
        this.setDeltaMovement(dir.scale(velocity));
        this.setYRot(yaw);
        this.setXRot(pitch);
    }

    private Vec3 findGroundPosition() {
        Level level = this.level();

        Vec3 start = this.position();
        Vec3 end = start.subtract(0, 50, 0); // check 50 blocks downward

        ClipContext context = new ClipContext(
                start,
                end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                this
        );

        BlockHitResult result = level.clip(context);

        if (result.getType() == HitResult.Type.BLOCK) {
            BlockPos hitPos = result.getBlockPos();
            return Vec3.atCenterOf(hitPos).add(0, 1, 0); // spawn above block
        }

        return start; // fallback
    }

    private StaffTier tier = StaffTier.Classic;

    public void setTier(StaffTier tier) {
        this.tier = tier;
    }

    public StaffTier getTier() {
        return this.tier;
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
