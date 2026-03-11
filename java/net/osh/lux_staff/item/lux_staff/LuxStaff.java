package net.osh.lux_staff.item.lux_staff;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.osh.lux_staff.item.lux_staff.tiers.ModTier;
import net.osh.lux_staff.item.lux_staff.tiers.StaffTier;
import net.osh.lux_staff.item.lux_staff.tiers.TierComponent;
import net.osh.lux_staff.skills.W.ThrownStaff;

public class LuxStaff extends Item implements ProjectileItem {
    private final StaffTier tier;

    protected LuxStaff(Properties properties, StaffTier tier) {
        super(properties);
        this.tier = tier;
    }

    public StaffTier getTier() {
        return this.tier;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment == null) return super.supportsEnchantment(stack, enchantment);

        if (enchantment.is(Enchantments.LOYALTY)) {
            return true;
        }
        return super.supportsEnchantment(stack, enchantment);
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        ThrownStaff staff = new ThrownStaff(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1));
        staff.pickup = AbstractArrow.Pickup.ALLOWED;
        return staff;
    }



    public interface AbilityProvider {
        boolean canCastQ(ItemStack stack, Player player);
        boolean canCastW(ItemStack stack, Player player);
        boolean canCastE(ItemStack stack, Player player);
        boolean canCastR(ItemStack stack, Player player);
    }
}
