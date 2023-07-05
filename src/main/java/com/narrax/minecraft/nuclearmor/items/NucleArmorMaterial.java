package com.narrax.minecraft.nuclearmor.items;

import com.narrax.minecraft.nuclearmor.NucleArmor;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
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
	public int getDurabilityForType(ArmorItem.Type armorType) {
		return armorType==ArmorItem.Type.CHESTPLATE ? 4800 : ArmorMaterials.IRON.getDurabilityForType(armorType);
	}

	@Override
	public int getDefenseForType(ArmorItem.Type armorType) {
		return ArmorMaterials.IRON.getDefenseForType(armorType);
	}

	@Override
	public int getEnchantmentValue() {
		return 0;
	}

	@Override
	public SoundEvent getEquipSound() {
		return ArmorMaterials.IRON.getEquipSound();
	}

	public TagKey<Item> getRepairTagKey() {
		return powerSource ? INGREDIENT_POWER_TAG : INGREDIENT_OTHER_TAG;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.of(getRepairTagKey());
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
