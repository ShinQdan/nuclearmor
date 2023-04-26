package com.narrax.minecraft.nuclearmor.items;

import com.narrax.minecraft.nuclearmor.NucleArmor;
import com.narrax.minecraft.nuclearmor.items.model.NucleArmorModel;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NucleArmor.MODID);

	private static final RegistryObject<Item> NUCLEARMOR_HELMET = ITEMS.register("nuclear_helmet", () -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_OTHER, EquipmentSlot.HEAD, NucleArmorModel.LAYER_HEAD));
	private static final RegistryObject<Item> NUCLEARMOR_CHESTPLATE = ITEMS.register("nuclear_chestplate", () -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_CHEST, EquipmentSlot.CHEST, NucleArmorModel.LAYER_CHEST));
	private static final RegistryObject<Item> NUCLEARMOR_LEGGINGS = ITEMS.register("nuclear_leggings", () -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_OTHER, EquipmentSlot.LEGS, NucleArmorModel.LAYER_LEGS));
	private static final RegistryObject<Item> NUCLEARMOR_BOOTS = ITEMS.register("nuclear_boots", () -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_OTHER, EquipmentSlot.FEET, NucleArmorModel.LAYER_FEET));
}
