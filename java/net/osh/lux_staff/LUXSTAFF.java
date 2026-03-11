package net.osh.lux_staff;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.osh.lux_staff.effects.Illumination.Illumination;
import net.osh.lux_staff.effects.ModEffect;
import net.osh.lux_staff.item.lux_staff.ModWeapon;
import net.osh.lux_staff.item.lux_staff.tiers.ModTier;
import net.osh.lux_staff.particles.IlluminationParticle;
import net.osh.lux_staff.particles.ModParticles;
import net.osh.lux_staff.skills.E.Projectile.LS_Render;
import net.osh.lux_staff.attachment.ModAttachment;
import net.osh.lux_staff.skills.E.Singularity.OrbRenderer;
import net.osh.lux_staff.skills.ModEntities;
import net.osh.lux_staff.skills.Q.LB_Render;
import net.osh.lux_staff.skills.R.FS_Render;
import net.osh.lux_staff.skills.W.StaffThrownRenderer;
import net.osh.lux_staff.uxui.HUDoverlay;
import net.osh.lux_staff.uxui.SkillsOverlay;
import net.osh.lux_staff.uxui.mana.ExpBarRemove;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(LUXSTAFF.MODID)
public class LUXSTAFF
{
    public static final String MODID = "lux_staff";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation res(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name);
    }
    public LUXSTAFF(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(Illumination.class);
        NeoForge.EVENT_BUS.register(HUDoverlay.class);
        NeoForge.EVENT_BUS.register(SkillsOverlay.class);
        NeoForge.EVENT_BUS.register(ExpBarRemove.class);
    //------------------------------------------------------------------------------------------------------------------
        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    //------------------------------------------------------------------------------------------------------------------
        ModWeapon.register(modEventBus);
        ModEffect.register(modEventBus);
        ModTier.register(modEventBus);
        ModEntities.register(modEventBus);
        ModAttachment.ATTACHMENT.register(modEventBus);
        ModParticles.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModWeapon.Lux_Classic_Staff.get());
            event.accept(ModWeapon.Lux_Air_Staff.get());
            event.accept(ModWeapon.Lux_Spirit_Blossom_Staff.get());
            event.accept(ModWeapon.Lux_Cosmic_Staff.get());
            event.accept(ModWeapon.Lux_Elementalist_Staff.get());
        }

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModWeapon.LUX_AIR_UPGRADE.get());
            event.accept(ModWeapon.LUX_BLOSSOM_UPGRADE.get());
            event.accept(ModWeapon.LUX_COSMIC_UPGRADE.get());
            event.accept(ModWeapon.LUX_ELEMENTALIST_UPGRADE.get());
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            EntityRenderers.register(ModEntities.LBP.get(), LB_Render::new);
            EntityRenderers.register(ModEntities.LSP.get(), LS_Render::new);
            EntityRenderers.register(ModEntities.THROWN_STAFF.get(), StaffThrownRenderer::new);
            EntityRenderers.register(ModEntities.ORB.get(), OrbRenderer::new);
            EntityRenderers.register(ModEntities.LASER.get(), FS_Render::new);
        }

        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticles.LUMINOUS_PARTICLE.get(), IlluminationParticle.Provider::new);
        }
    }
}
