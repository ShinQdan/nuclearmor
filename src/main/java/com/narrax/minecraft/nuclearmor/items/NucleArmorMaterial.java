package com.narrax.minecraft.nuclearmor.items;

import com.narrax.minecraft.nuclearmor.NucleArmor;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public class NucleArmorMaterial implements ArmorMaterial {
	public static final NucleArmorMaterial NUCLEAR_MATERIAL_CHEST = new NucleArmorMaterial(true);
	public static final NucleArmorMaterial NUCLEAR_MATERIAL_OTHER = new NucleArmorMaterial(false);

	public static final TagKey<Item> INGREDIENT_POWER_TAG = ItemTags.create(new ResourceLocation("forge", "ingots/uranium"));
	public static final TagKey<Item> INGREDIENT_OTHER_TAG = ItemTags.create(new ResourceLocation("forge", "ingots/iron"));

	protected final boolean powerSource;

	private NucleArmorMaterial(boolean repairable){
		this.powerSource = repairable;
	}

	@Override
	public int getDurabilityForSlot(EquipmentSlot slot) {
		return slot==EquipmentSlot.CHEST ? 1620 : ArmorMaterials.IRON.getDurabilityForSlot(slot)+100;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot slot) {
		return ArmorMaterials.IRON.getDefenseForSlot(slot);
	}

	@Override
	public int getEnchantmentValue() {
		return 0;
	}

	@Override
	public SoundEvent getEquipSound() {
		return ArmorMaterials.IRON.getEquipSound();
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.of(powerSource ? null : INGREDIENT_OTHER_TAG);
	}

	@Override
	public String getName() {
		return NucleArmor.MODID+":nuclear_armor";
	}

	@Override
	public float getToughness() {
		return ArmorMaterials.IRON.getToughness();
	}

	@Override
	public float getKnockbackResistance() {
		return 0.25f;
	}
	
}
