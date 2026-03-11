package net.osh.lux_staff.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.osh.lux_staff.LUXSTAFF;
import net.osh.lux_staff.item.lux_staff.ModWeapon;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider {
    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new ByTheLight()));
    }

    public static class ByTheLight implements AdvancementGenerator {

        @Override
        public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(
                            ModWeapon.Lux_Classic_Staff.get(), // icon for the tab
                            Component.literal("Luxanna Crownguard"),
                            Component.literal("Harness the power of light"),
                            ResourceLocation.fromNamespaceAndPath(
                                    LUXSTAFF.MODID,
                                    "textures/gui/advancements/stone_bricks.png"
                            ),
                            AdvancementType.TASK,
                            false, false, false
                    )
                    .addCriterion(
                            "has_staff",
                            InventoryChangeTrigger.TriggerInstance.hasItems(ModWeapon.Lux_Classic_Staff.get())
                    )
                    .save(consumer, String.valueOf(ResourceLocation.fromNamespaceAndPath(LUXSTAFF.MODID, "root")));

            Advancement.Builder.advancement()
                    .parent(root)
                    .display(
                            ModWeapon.Lux_Elementalist_Staff.get(),
                            Component.literal("BY THE LIGHT!"),
                            Component.literal("Obtain the ultimate elemental magic"),
                            null,
                            AdvancementType.CHALLENGE, true, true, false
                    )
                    .addCriterion("obtain_staff",
                            InventoryChangeTrigger.TriggerInstance.hasItems(ModWeapon.Lux_Elementalist_Staff.get())
                    )
                    .save(consumer, String.valueOf(ResourceLocation.fromNamespaceAndPath("lux_staff", "by_the_light")));
        }
    }
}
