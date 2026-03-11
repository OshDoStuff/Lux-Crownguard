package net.osh.lux_staff.item.upgrade;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class LuxUpgradeTemplate extends SmithingTemplateItem {
    public LuxUpgradeTemplate(Component appliesTo,
                              Component ingredients,
                              Component upgradeDescription,
                              Component baseSlotDescription,
                              Component additionsSlotDescription,
                              List<ResourceLocation> baseSlotEmptyIcons,
                              List<ResourceLocation> additionalSlotEmptyIcons) {
        super(appliesTo, ingredients, upgradeDescription, baseSlotDescription, additionsSlotDescription, baseSlotEmptyIcons, additionalSlotEmptyIcons);
    }
}
