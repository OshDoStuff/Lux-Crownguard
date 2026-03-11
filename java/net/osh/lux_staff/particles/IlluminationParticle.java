package net.osh.lux_staff.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import javax.annotation.Nullable;

public class IlluminationParticle extends TextureSheetParticle implements ParticleOptions {
    protected IlluminationParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet,
                                                        double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y + 1, z, xSpeed, ySpeed, zSpeed);

        this.friction = 1f;

        this.xd = (double)0.0F;
        this.yd = 0.1;
        this.zd = (double)0.0F;

        this.lifetime = 15;
        this.setSpriteFromAge(spriteSet);

        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 255f;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public ParticleType<?> getType() {
        return getType();
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel,
                                       double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new IlluminationParticle(clientLevel, pX, pY, pZ, this.spriteSet, pXSpeed, pYSpeed, pZSpeed);
        }
    }
}
