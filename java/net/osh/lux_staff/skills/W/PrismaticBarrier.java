package net.osh.lux_staff.skills.W;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.osh.lux_staff.attachment.ModAttachment;
import net.osh.lux_staff.item.lux_staff.LuxStaff;
import net.osh.lux_staff.item.lux_staff.tiers.StaffTier;
import net.osh.lux_staff.mana.PlayerMana;

public class PrismaticBarrier {

    public static void cast(ServerPlayer player) {
        StaffTier tier = StaffTier.Classic;
        System.out.println("Prismatic Barrier");
        Level level = player.level();

        ItemStack stack = ItemStack.EMPTY;
        if (player.getMainHandItem().getItem() instanceof LuxStaff staff) {
            stack = player.getMainHandItem();
            tier = staff.getTier();
        } else if (player.getOffhandItem().getItem() instanceof LuxStaff staff) {
            stack = player.getOffhandItem();
            tier = staff.getTier();
        }

        int effectAmp = tier.effectAmplifier();
        int mana = tier.WmanaCost();

        if (stack.isEmpty()) return;

        if (!PlayerMana.consume(player, mana)) return;
        if (player.getData(ModAttachment.W_COOLDOWN.get()) > 0) return;

        ThrownStaff thrown = new ThrownStaff(level, player, stack.copy( ), tier);

            thrown.setPos(player.getX(), player.getEyeY() -.1, player.getZ());
            thrown.shootFromRotation(
                    player, player.getXRot(), player.getYRot(),
                    0F, 2.5F, 0F
            );
        if (player.hasInfiniteMaterials()) {
            thrown.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }
        thrown.setTier(tier);

        level.addFreshEntity(thrown);
        if (!player.hasInfiniteMaterials()) {
            player.getInventory().removeItem(stack);
        }

        player.addEffect(new MobEffectInstance(
                MobEffects.ABSORPTION,
                20 * 10,
                effectAmp,
                false,
                true
        ));
        int cooldown = tier.Wcooldown();

        player.setData(ModAttachment.W_COOLDOWN.get(), 20 * cooldown);
    }
}
