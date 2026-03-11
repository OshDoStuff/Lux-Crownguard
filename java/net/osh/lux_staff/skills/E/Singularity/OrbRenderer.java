package net.osh.lux_staff.skills.E.Singularity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.osh.lux_staff.LUXSTAFF;

public class OrbRenderer extends EntityRenderer<SingularityOrb> {

    private SingularityModel model;

    public OrbRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SingularityModel(context.bakeLayer(SingularityModel.LAYER_LOCATION));
    }

    @Override
    public void render(SingularityOrb entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        float time = entity.tickCount + partialTick;

        poseStack.translate(0.0D, -0.5D, 0.0D);

        poseStack.mulPose(Axis.YP.rotationDegrees(time * 2.0f));

        float scale = 1.0f + Mth.sin(time * 0.15f) * 0.15f;
        poseStack.scale(scale, scale, scale);

        VertexConsumer vertexConsumer =
                bufferSource.getBuffer(RenderType.entityTranslucent(getTextureLocation(entity)));

        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SingularityOrb singularityOrb) {
        return ResourceLocation.fromNamespaceAndPath(LUXSTAFF.MODID, "textures/entity/singularity/singularity.png");
    }
}
