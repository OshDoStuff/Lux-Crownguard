package net.osh.lux_staff.item.lux_staff;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.osh.lux_staff.LUXSTAFF;
import net.osh.lux_staff.item.lux_staff.staves.*;
import net.osh.lux_staff.item.lux_staff.tiers.ModTier;
import net.osh.lux_staff.item.lux_staff.tiers.StaffTier;
import net.osh.lux_staff.item.lux_staff.tiers.TierComponent;
import net.osh.lux_staff.item.upgrade.LuxUpgradeTemplate;

import java.util.List;

public class ModWeapon {
    public static final DeferredRegister<Item> ITEM =
            DeferredRegister.createItems(LUXSTAFF.MODID);

    //------------------------------------------------------------------------------------------------------------------

        public static final DeferredHolder<Item, Item> Lux_Elementalist_Staff = ITEM.register("lux_elementalist_staff",
                () -> new Elementalist(new Item.Properties().stacksTo(1)));

        public static final DeferredHolder<Item, Item> Lux_Cosmic_Staff = ITEM.register("lux_cosmic_staff",
                () -> new Cosmic(new Item.Properties().stacksTo(1)));

        public static final DeferredHolder<Item, Item> Lux_Spirit_Blossom_Staff = ITEM.register("lux_spirit_blossom_staff",
                () -> new SpiritBlossom(new Item.Properties().stacksTo(1)));

        public static final DeferredHolder<Item, Item> Lux_Air_Staff = ITEM.register("lux_air_staff",
                () -> new Air(new Item.Properties().stacksTo(1)));

        public static final DeferredHolder<Item, Item> Lux_Classic_Staff = ITEM.register("lux_classic_staff",
                () -> new Classic(new Item.Properties().stacksTo(1)));

    //-- UPGRADE TEMPLATES ---------------------------------------------------------------------------------------------

        public static final DeferredHolder<Item, Item> LUX_AIR_UPGRADE = ITEM.register("lux_air_upgrade",
                () -> new LuxUpgradeTemplate(
                        Component.translatable("item.lux_staff.lux_upgrade_template.applies_to"),
                        Component.translatable("item.lux_staff.lux_upgrade_template.ingredients"),
                        Component.translatable("item.lux_staff.lux_air_upgrade_template"),
                        Component.translatable("item.lux_staff.lux_upgrade_template.base_slot"),
                        Component.translatable("item.lux_staff.lux_upgrade_template.additions_slot"),
                        List.of(ResourceLocation.withDefaultNamespace("item/empty_slot_sword")),
                        List.of(ResourceLocation.withDefaultNamespace("item/empty_slot_ingot"))
                ));

        public static final DeferredHolder<Item, Item> LUX_BLOSSOM_UPGRADE = ITEM.register("lux_blossom_upgrade",
                () -> new LuxUpgradeTemplate(
                        Component.translatable("item.lux_staff.lux_upgrade_template.applies_to"),
                        Component.translatable("item.lux_staff.lux_upgrade_template.ingredients"),
                        Component.translatable("item.lux_staff.lux_blossom_upgrade_template"),
                        Component.translatable("item.lux_staff.lux_upgrade_template.base_slot"),
                        Component.translatable("item.lux_staff.lux_upgrade_template.additions_slot"),
                        List.of(ResourceLocation.withDefaultNamespace("item/empty_slot_sword")),
                        List.of(ResourceLocation.withDefaultNamespace("item/empty_slot_ingot"))
                ));

        public static final DeferredHolder<Item, Item> LUX_COSMIC_UPGRADE = ITEM.register("lux_cosmic_upgrade",
                () -> new LuxUpgradeTemplate(
                        Component.translatable("item.lux_staff.lux_upgrade_template.applies_to"),
                        Component.translatable("item.lux_staff.lux_upgrade_template.ingredients"),
                        Component.translatable("item.lux_staff.lux_cosmic_upgrade_template"),
                        Component.translatable("item.lux_staff.lux_upgrade_template.base_slot"),
                        Component.translatable("item.lux_staff.lux_upgrade_template.additions_slot"),
                        List.of(ResourceLocation.withDefaultNamespace("item/empty_slot_sword")),
                        List.of(ResourceLocation.withDefaultNamespace("item/empty_slot_ingot"))
                ));

        public static final DeferredHolder<Item, Item> LUX_ELEMENTALIST_UPGRADE = ITEM.register("lux_elementalist_upgrade",
                () -> new LuxUpgradeTemplate(
                        Component.translatable("item.lux_staff.lux_upgrade_template.applies_to"),
                        Component.translatable("item.lux_staff.lux_upgrade_template.ingredients"),
                        Component.translatable("item.lux_staff.lux_elementalist_upgrade_template"),
                        Component.translatable("item.lux_staff.lux_upgrade_template.base_slot"),
                        Component.translatable("item.lux_staff.lux_upgrade_template.additions_slot"),
                        List.of(ResourceLocation.withDefaultNamespace("item/empty_slot_sword")),
                        List.of(ResourceLocation.withDefaultNamespace("item/empty_slot_ingot"))
                ));

    //------------------------------------------------------------------------------------------------------------------

    public static void register(IEventBus eventBus) {
        ITEM.register(eventBus);
    }
}
