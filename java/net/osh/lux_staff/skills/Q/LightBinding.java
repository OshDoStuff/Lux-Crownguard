package net.osh.lux_staff.skills.Q;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.datafix.fixes.ItemWaterPotionFix;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.osh.lux_staff.attachment.ModAttachment;
import net.osh.lux_staff.cooldown.CooldownKeys;
import net.osh.lux_staff.item.lux_staff.LuxStaff;
import net.osh.lux_staff.item.lux_staff.tiers.ModTier;
import net.osh.lux_staff.item.lux_staff.tiers.StaffTier;
import net.osh.lux_staff.item.lux_staff.tiers.TierComponent;
import net.osh.lux_staff.mana.PlayerMana;


public class LightBinding {

    public static void cast(ServerPlayer player) {
        Item main = player.getMainHandItem().getItem();
        Item off = player.getOffhandItem().getItem();

        Item stack = main instanceof LuxStaff ? main : off;

        System.out.println("Light Binding");
        Level level = player.level();
        if (level.isClientSide) return;
        if (!PlayerMana.consume(player, 50)) return;
        if (player.getData(ModAttachment.Q_COOLDOWN.get()) > 0) return;

        LB_Projectile projectile = new LB_Projectile(level, player);
        if (stack instanceof LuxStaff staff ) {
            projectile.setTier(staff.getTier());
        }

        projectile.shootFromRotation(
                player,
                player.getXRot(),
                player.getYRot(),
                0.0F, 1.5F, 0.0F
        );

        level.addFreshEntity(projectile);
        player.getCooldowns().addCooldown(stack, 5);
        player.setData(ModAttachment.Q_COOLDOWN.get(), 20 * 10);
    }
}
