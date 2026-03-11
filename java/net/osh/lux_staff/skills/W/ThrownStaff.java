package net.osh.lux_staff.skills.W;

import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.osh.lux_staff.item.lux_staff.ModWeapon;
import net.osh.lux_staff.item.lux_staff.tiers.StaffTier;
import net.osh.lux_staff.skills.ModEntities;

public class ThrownStaff extends AbstractArrow {
    public float rotation;
    public Vec2 groundedOffset;
    private StaffTier staffTier;

    public ThrownStaff(EntityType<? extends ThrownStaff> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownStaff(Level level, LivingEntity shooter, ItemStack stack, StaffTier tier) {
        super(ModEntities.THROWN_STAFF.get(), shooter, level, stack.copy(), null);
        this.setPickupItemStack(stack);
        this.staffTier = tier;
        this.entityData.set(LOYALTY, this.getLoyalty(this.getPickupItemStackOrigin()));
    }

    public ThrownStaff(Level level, double x, double y, double z, ItemStack stack) {
        super(ModEntities.THROWN_STAFF.get(),level);
    }

    private static boolean isLuxAlly(LivingEntity caster, LivingEntity target) {
        if (caster == target) return false;

        if (target instanceof Player && caster instanceof Player) {
            return true;
        }
        return caster.isAlliedTo(target);
    }

    protected boolean tryPickup(Player player) {
        return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (this.level().isClientSide) return;
        StaffTier tier = this.getTier();

        Entity hit = result.getEntity();
        Entity owner = this.getOwner();
        int amp = tier.effectAmplifier();

        if (!(owner instanceof LivingEntity caster)) return;

        if (hit instanceof LivingEntity target) {
            if (isLuxAlly(caster, target)) {
                target.addEffect(new MobEffectInstance(
                        MobEffects.ABSORPTION,
                        20*8, amp, false, true
                ));
            }
        }
    }

    @Override
    public void playerTouch(Player player) {
        StaffTier tier = this.getTier();

        int amp = tier.effectAmplifier();
        int add = tier.effectAddition();

        if (player == null) {
            super.playerTouch(player);
            return;
        }

        if (!this.level().isClientSide) {
            boolean touchedGround = this.entityData.get(GROUNDED_BOOLEAN);
            if (touchedGround) {
                player.addEffect(new MobEffectInstance(
                        MobEffects.ABSORPTION,
                        20 * 10, // 10 seconds
                        amp + add,
                        false,
                        true
                ));
            }
        }

        if (this.ownedBy(player) || this.getOwner() == null) {
            super.playerTouch(player);
        }
    }

//-- LOYALTY ---------------------------------------------------------------------------------------------------------\\
    public int clientSideReturnTickCount;

    private byte getLoyalty(ItemStack stack) {
        Level level = this.level();
        if (level instanceof ServerLevel serverLevel) {
            return (byte) Mth.clamp(
                    EnchantmentHelper.getTridentReturnToOwnerAcceleration(serverLevel, stack, this),
                    0,
                    127
            );
        }
        return 0;
    }

    private boolean acceptableReturnOwner() {
        Entity entity = this.getOwner();
        return entity != null && entity.isAlive() ? !(entity instanceof ServerPlayer) || !entity.isSpectator() : false;
    }

    @Override
    public void tick() {
        Entity entity = this.getOwner();
        int i = (Byte) this.entityData.get(LOYALTY);
        if (i > 0 && (this.inGround || this.isNoPhysics() && entity != null)) {
            if (!this.acceptableReturnOwner()) {
                if (!this.level().isClientSide && this.pickup == Pickup.ALLOWED ) {
                    this.spawnAtLocation(this.getPickupItem(), .1F);
                }
                this.discard();
            } else {
                if (!this.isNoPhysics()) {
                    this.setDeltaMovement(Vec3.ZERO);
                }
                this.setNoPhysics(true);

                Vec3 vec3 = entity.position().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015 * (double)i, this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }

                double d = 0.05 * (double) i;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95).add(vec3.normalize().scale(d)));
                if (this.clientSideReturnTickCount == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.clientSideReturnTickCount;
            }
        }
        super.tick();
    }

    public void tickDespawn() {
        int i = (Byte)this.entityData.get(LOYALTY);
        if (this.pickup != Pickup.ALLOWED || i <= 0) {
            super.tickDespawn();
        }

    }

    //--------------------------------------------------------------------------------------------------------------------\\

    @Override
    protected ItemStack getDefaultPickupItem() {
        StaffTier tier = this.getTier();

        return switch (tier) {
            case Elementalist -> ModWeapon.Lux_Elementalist_Staff.get().getDefaultInstance();
            case Cosmic -> ModWeapon.Lux_Cosmic_Staff.get().getDefaultInstance();
            case Blossom -> ModWeapon.Lux_Spirit_Blossom_Staff.get().getDefaultInstance();
            case Air -> ModWeapon.Lux_Air_Staff.get().getDefaultInstance();
            case Classic -> ModWeapon.Lux_Classic_Staff.get().getDefaultInstance();
        };
    }

    public float getRenderingRotation() {
        rotation += 0.5f;
        if(rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    public boolean isGrounded() {
        return inGround;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.entityData.set(GROUNDED_BOOLEAN, true);

        if(result.getDirection() == Direction.SOUTH) {
            groundedOffset = new Vec2(215f,180f);
        }
        if(result.getDirection() == Direction.NORTH) {
            groundedOffset = new Vec2(215f, 0f);
        }
        if(result.getDirection() == Direction.EAST) {
            groundedOffset = new Vec2(215f,-90f);
        }
        if(result.getDirection() == Direction.WEST) {
            groundedOffset = new Vec2(215f,90f);
        }

        if(result.getDirection() == Direction.DOWN) {
            groundedOffset = new Vec2(115f,180f);
        }
        if(result.getDirection() == Direction.UP) {
            groundedOffset = new Vec2(285f,180f);
        }
    }

    private static final EntityDataAccessor<Integer> TIER_ID =
            SynchedEntityData.defineId(ThrownStaff.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> GROUNDED_BOOLEAN =
            SynchedEntityData.defineId(ThrownStaff.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Byte> LOYALTY =
            SynchedEntityData.defineId(ThrownStaff.class, EntityDataSerializers.BYTE);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TIER_ID, 0);
        builder.define(GROUNDED_BOOLEAN, false);
        builder.define(LOYALTY, (byte) 0);
    }

    public void setTier(StaffTier tier) {
        this.entityData.set(TIER_ID, tier.ordinal());
    }

    public StaffTier getTier() {
        return StaffTier.values()[this.entityData.get(TIER_ID)];
    }
}
