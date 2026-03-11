package net.osh.lux_staff.effects.Illumination;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.osh.lux_staff.datagen.ModDamageTypes;
import net.osh.lux_staff.effects.ModEffect;
import net.osh.lux_staff.particles.ModParticles;

public class Illumination extends MobEffect {

    public Illumination(MobEffectCategory category, int color) {
        super(MobEffectCategory.NEUTRAL, color);
    }

    @Override
    public ParticleOptions createParticleOptions(MobEffectInstance effect) {
        return ModParticles.LUMINOUS_PARTICLE.get();
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        if (target.level().isClientSide) return;

        DamageSource source = event.getSource();

        MobEffectInstance illu = target.getEffect(ModEffect.ILLUMINATION);
        if (illu == null) return;

        if (source.is(ModDamageTypes.ILLUMINATION)) return;

        IlluminationContext.enter();
        try {
            float damage = IlluminationScaling.illumination(
                    illu.getAmplifier(),
                    0
            );
            target.removeEffect(ModEffect.ILLUMINATION);
            target.hurt(target.damageSources().source(
                    ModDamageTypes.ILLUMINATION),
                    damage);

        } finally {
            IlluminationContext.exit();
        }
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }
}
