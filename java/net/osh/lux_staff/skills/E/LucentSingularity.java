package net.osh.lux_staff.skills.E;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.osh.lux_staff.cooldown.CooldownKeys;
import net.osh.lux_staff.item.lux_staff.LuxStaff;
import net.osh.lux_staff.item.lux_staff.tiers.StaffTier;
import net.osh.lux_staff.mana.PlayerMana;
import net.osh.lux_staff.skills.E.Projectile.LS_Projectile;
import net.osh.lux_staff.attachment.ModAttachment;
import net.osh.lux_staff.skills.E.Singularity.SingularityOrb;

public class LucentSingularity {

    public static void cast(ServerPlayer player) {
        StaffTier tier = StaffTier.Classic;
        System.out.println("Lucent Singularity");
        Level level = player.level();
        if (level.isClientSide) return;

        ItemStack stack = ItemStack.EMPTY;
        if (player.getMainHandItem().getItem() instanceof LuxStaff staff) {
            stack = player.getMainHandItem();
            tier = staff.getTier();
        } else if (player.getOffhandItem().getItem() instanceof LuxStaff staff) {
            stack = player.getOffhandItem();
            tier = staff.getTier();

        }

        int mana = tier.EmanaCost();

        if (stack.isEmpty()) return;

        boolean active = player.getData(ModAttachment.ACTIVE_SINGULARITY.get());
        if (!active) {
            if (!PlayerMana.consume(player, mana)) return;
            if (player.getData(ModAttachment.E_COOLDOWN.get()) > 0) return;

            player.setData(ModAttachment.ACTIVE_SINGULARITY.get(), true);
            LS_Projectile projectile = new LS_Projectile(level, player);
            projectile.setTier(tier);

            projectile.shootFromRotation(
                    player,
                    player.getXRot(),
                    player.getYRot(),
                    0.0F,   // no arc
                    1.0F,   // speed (tweak later)
                    0.0F    // no inaccuracy
            );

            player.getCooldowns().addCooldown(stack.getItem(), 5);
            level.addFreshEntity(projectile);
        } else {
            SingularityOrb orb = level.getEntitiesOfClass(
                    SingularityOrb.class,
                    player.getBoundingBox().inflate(128),
                    o -> o.getOwner() == player
            ).stream().findFirst().orElse(null);
            if (orb != null) {
                orb.explode();
            }
        }
    }
}
