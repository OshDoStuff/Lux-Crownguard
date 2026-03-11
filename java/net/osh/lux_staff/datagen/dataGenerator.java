package net.osh.lux_staff.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.osh.lux_staff.LUXSTAFF;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = LUXSTAFF.MODID)
public class dataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();
        registries = generator.addProvider(true, new RegistryProvider(output, registries)).getRegistryProvider();

        generator.addProvider(true, new ModDamageTagProvider(output, registries, helper));
        generator.addProvider(true, new ModAdvancementProvider(output, registries, helper));
    }
}