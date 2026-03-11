package net.osh.lux_staff.skills.E.Singularity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.osh.lux_staff.LUXSTAFF;

public class SingularityModel<T extends SingularityOrb> extends HierarchicalModel<T> {
        public static final ModelLayerLocation LAYER_LOCATION =
                new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(LUXSTAFF.MODID, "singularity"), "main");

        private final ModelPart bb_main;

        public SingularityModel(ModelPart root) {
            this.bb_main = root.getChild("singularity");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition singularity = partdefinition.addOrReplaceChild("singularity", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 10.0F, 10.0F,
                        new CubeDeformation(0.0F)).texOffs(0, 20).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

        @Override
        public void setupAnim(SingularityOrb entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
            bb_main.render(poseStack, vertexConsumer, 0xF000F0, packedOverlay, color);
        }

        @Override
        public ModelPart root() {
            return bb_main;
        }
}
