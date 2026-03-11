package net.osh.lux_staff.skills.W;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.osh.lux_staff.item.lux_staff.ModWeapon;
import net.osh.lux_staff.item.lux_staff.tiers.StaffTier;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class StaffThrownRenderer extends EntityRenderer<ThrownStaff> {
    private final ItemRenderer itemRenderer;

    public StaffThrownRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer= context.getItemRenderer();
    }

    @Override
    public void render(ThrownStaff entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        StaffTier tier = entity.getTier();

        ItemStack itemstack = switch (tier){
            case Elementalist -> ModWeapon.Lux_Elementalist_Staff.get().getDefaultInstance();
            case Cosmic -> ModWeapon.Lux_Cosmic_Staff.get().getDefaultInstance();
            case Blossom -> ModWeapon.Lux_Spirit_Blossom_Staff.get().getDefaultInstance();
            case Air -> ModWeapon.Lux_Air_Staff.get().getDefaultInstance();
            case Classic -> ModWeapon.Lux_Classic_Staff.get().getDefaultInstance();
        };

        poseStack.pushPose();
        poseStack.scale(2.0F, 2.0F, 2.0F);

        if(!entity.isGrounded()) {
            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot())));
            poseStack.mulPose(Axis.XP.rotationDegrees(entity.getRenderingRotation() * 5f));
        }

        itemRenderer.renderStatic(itemstack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownStaff entity) {
        return ResourceLocation.withDefaultNamespace("textures/item/lux_classic_staff.png");
    }

}
