package net.osh.lux_staff.skills.R;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.osh.lux_staff.attachment.ModAttachment;
import net.osh.lux_staff.effects.ModEffect;
import net.osh.lux_staff.item.lux_staff.LuxStaff;
import net.osh.lux_staff.item.lux_staff.tiers.StaffTier;
import net.osh.lux_staff.mana.PlayerMana;
import net.osh.lux_staff.skills.ModEntities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FinalSpark {
    public static void cast(ServerPlayer player) {
        System.out.println("Final Spark");
        Level level = player.level();

        if (level.isClientSide) return;
        if (!PlayerMana.consume(player, 100)) return;
        if (player.getData(ModAttachment.R_COOLDOWN.get()) > 0) return;
        if (!player.isAlive()) return;

        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof LuxStaff staff)) return;
        StaffTier tier = staff.getTier();

        int cooldown = tier.Rcooldown();

        FS_Entity beam = new FS_Entity(ModEntities.LASER.get(), level);
        beam.setup(player);

        ((ServerLevel) level).addFreshEntity(beam);

        player.getCooldowns().addCooldown(stack.getItem(), 5);
        player.setData(ModAttachment.R_COOLDOWN.get(), 20 * cooldown);
    }


}
