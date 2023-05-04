package com.narrax.minecraft.nuclearmor.items;

import com.narrax.minecraft.nuclearmor.NucleArmor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = NucleArmor.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NucleArmor.MODID);

	private static final RegistryObject<Item> NUCLEARMOR_HELMET = ITEMS.register("nuclear_helmet", () -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_OTHER, EquipmentSlot.HEAD));
	private static final RegistryObject<Item> NUCLEARMOR_CHESTPLATE = ITEMS.register("nuclear_chestplate", () -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_CHEST, EquipmentSlot.CHEST));
	private static final RegistryObject<Item> NUCLEARMOR_LEGGINGS = ITEMS.register("nuclear_leggings", () -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_OTHER, EquipmentSlot.LEGS));
	private static final RegistryObject<Item> NUCLEARMOR_BOOTS = ITEMS.register("nuclear_boots", () -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_OTHER, EquipmentSlot.FEET));

	@SubscribeEvent
	public static void creativeTabBuildContents(CreativeModeTabEvent.BuildContents event){
		if(event.getTab()==CreativeModeTabs.COMBAT){
			ITEMS.getEntries().forEach(entry -> event.accept(entry.get()));
		}
	}
}
