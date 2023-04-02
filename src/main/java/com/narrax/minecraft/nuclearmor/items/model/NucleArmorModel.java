package com.narrax.minecraft.nuclearmor.items.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class NucleArmorModel extends HumanoidModel<LivingEntity> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "nuclearmormodel"), "main");
	public static final ModelPart BAKED_GENERAL = createBodyLayer(true).bakeRoot();
	public static final ModelPart BAKED_FEET = createBodyLayer(false).bakeRoot();
	// private final ModelPart rightFoot;
	// private final ModelPart leftFoot;

	public NucleArmorModel(EquipmentSlot slot, HumanoidModel original) {
		super(slot!=EquipmentSlot.FEET ? BAKED_GENERAL : BAKED_FEET);
		// this.rightFoot = BAKED.getChild("right_foot");
		// this.leftFoot = BAKED.getChild("left_foot");
		setAllVisible(false);
		switch(slot){
			case HEAD: this.head.visible = true; break;
			case CHEST: this.body.visible = this.leftArm.visible = this.rightArm.visible = true; break;
			case LEGS: 
			case FEET: this.leftLeg.visible = this.rightLeg.visible = true; break;
			default: break;
		}

		//theoretically unsafe, practically not
		original.copyPropertiesTo(this);
	}

	public static LayerDefinition createBodyLayer(boolean legs) {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 44).addBox(-4.0F, -8.0F, 4.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 19).addBox(-5.0F, -8.0F, -5.0F, 1.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 11).addBox(4.0F, -8.0F, -5.0F, 1.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(17, 58).addBox(-4.0F, -8.0F, -5.0F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(46, 55).addBox(-4.0F, -3.0F, -5.0F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 43).addBox(-3.0F, -2.0F, -6.0F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 11).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(44, 27).addBox(-3.0F, 1.0F, 2.0F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(16, 38).addBox(-4.0F, 0.0F, 2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(30, 0).addBox(-4.0F, 6.0F, 2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 11).addBox(3.0F, 1.0F, 2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, 1.0F, 2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(59, 27).addBox(-3.0F, 2.0F, 5.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 27).addBox(-3.0F, 4.0F, 5.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 53).addBox(-4.0F, 1.0F, -3.0F, 8.0F, 5.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 59).addBox(-3.0F, 6.0F, -3.0F, 6.0F, 6.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(48, 47).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.35F))
		.texOffs(48, 18).addBox(-3.0F, 6.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.35F))
		.texOffs(0, 29).addBox(-2.0F, 2.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.5F))
		.texOffs(6, 9).addBox(-3.0F, 2.0F, -1.0F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		right_arm.addOrReplaceChild("Top_r1", CubeListBuilder.create().texOffs(0, 37).addBox(-4.0F, 2.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(48, 35).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.35F))
		.texOffs(48, 9).addBox(-1.0F, 6.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.35F))
		.texOffs(12, 11).addBox(0.0F, 2.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.5F))
		.texOffs(0, 9).addBox(3.0F, 2.0F, -1.0F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		left_arm.addOrReplaceChild("Top_r2", CubeListBuilder.create().texOffs(28, 31).addBox(-1.0F, 2.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

		if(legs){
			partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(18, 43).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.35F))
			.texOffs(60, 7).addBox(-1.0F, 7.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(-1.9F, 12.0F, 0.0F));
			partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(36, 39).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.35F))
			.texOffs(35, 58).addBox(-0.8F, 7.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(1.9F, 12.0F, 0.0F));
		}else{
			partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(54, 0).addBox(-2.1F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.35F))
			.texOffs(6, 0).addBox(-2.0F, 7.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(-1.9F, 12.0F, 0.0F));
			partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(30, 51).addBox(-1.9F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.35F))
			.texOffs(0, 0).addBox(2.1F, 7.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(1.9F, 12.0F, 0.0F));
		}
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}