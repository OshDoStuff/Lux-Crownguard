package net.osh.lux_staff.modevents;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.osh.lux_staff.LUXSTAFF;
import net.osh.lux_staff.skills.E.Singularity.SingularityModel;

@EventBusSubscriber(modid = LUXSTAFF.MODID, value = Dist.CLIENT)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SingularityModel.LAYER_LOCATION, SingularityModel::createBodyLayer);
    }

    //@SubscribeEvent
    //public static void registerAttributes(EntityAttributeCreationEvent event) {
    //    event.put(ModEntities.ORB.get(), SingularityOrb.);
    //}
}
