package com.narrax.minecraft.nuclearmor.items;

import java.util.EnumMap;
import java.util.List;

import com.narrax.minecraft.nuclearmor.NucleArmor;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public class NucleArmorMaterial {
	public static final TagKey<Item> INGREDIENT_POWER_TAG = ItemTags.create(new ResourceLocation("forge", "ingots/uranium"));
	public static final TagKey<Item> INGREDIENT_OTHER_TAG = ItemTags.create(new ResourceLocation("forge", "ingots/iron"));

	public static final String NUCLEAR_NAME_CHEST = NucleArmor.MODID+":nuclear_armor_chest";

	public static final Holder<ArmorMaterial> NUCLEAR_MATERIAL_CHEST = Registry.registerForHolder(
		BuiltInRegistries.ARMOR_MATERIAL, 
		new ResourceLocation(NucleArmor.MODID, "nuclear_armor_chest"),
		new ArmorMaterial(
			Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
				map.put(ArmorItem.Type.BODY, ArmorMaterials.IRON.value().getDefense(ArmorItem.Type.BODY));
			}),
			0,
			ArmorMaterials.IRON.value().equipSound(),
			() -> Ingredient.of(INGREDIENT_POWER_TAG),
			List.of(
				new ArmorMaterial.Layer(new ResourceLocation(NucleArmor.MODID, "nuclear_armor"), "", false),
				new ArmorMaterial.Layer(new ResourceLocation(NucleArmor.MODID, "nuclear_armor"), "", false)
			),
			ArmorMaterials.IRON.value().toughness(),
			ArmorMaterials.IRON.value().knockbackResistance()
		)
	);
	public static final Holder<ArmorMaterial> NUCLEAR_MATERIAL_OTHER = Registry.registerForHolder(
		BuiltInRegistries.ARMOR_MATERIAL, 
		new ResourceLocation(NucleArmor.MODID, "nuclear_armor_other"),
		new ArmorMaterial(
			Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
				map.put(ArmorItem.Type.BODY, ArmorMaterials.IRON.value().getDefense(ArmorItem.Type.BODY));
			}),
			0,
			ArmorMaterials.IRON.value().equipSound(),
			() -> Ingredient.of(INGREDIENT_OTHER_TAG),
			List.of(
				new ArmorMaterial.Layer(new ResourceLocation(NucleArmor.MODID, "nuclear_armor"), "", false),
				new ArmorMaterial.Layer(new ResourceLocation(NucleArmor.MODID, "nuclear_armor"), "", false)
			),
			ArmorMaterials.IRON.value().toughness(),
			ArmorMaterials.IRON.value().knockbackResistance()
		)
	);
}
