package com.narrax.minecraft.nuclearmor.items;

import com.narrax.minecraft.nuclearmor.NucleArmor;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = NucleArmor.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NucleArmor.MODID);

	private static final RegistryObject<Item> NUCLEARMOR_HELMET = ITEMS.register("nuclear_helmet", () -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_OTHER, ArmorItem.Type.HELMET));
	private static final RegistryObject<Item> NUCLEARMOR_CHESTPLATE = ITEMS.register("nuclear_chestplate", () -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_CHEST, ArmorItem.Type.CHESTPLATE));
	private static final RegistryObject<Item> NUCLEARMOR_LEGGINGS = ITEMS.register("nuclear_leggings", () -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_OTHER, ArmorItem.Type.LEGGINGS));
	private static final RegistryObject<Item> NUCLEARMOR_BOOTS = ITEMS.register("nuclear_boots", () -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_OTHER, ArmorItem.Type.BOOTS));

	@SubscribeEvent
	public static void creativeTabBuildContents(BuildCreativeModeTabContentsEvent event){
		if(event.getTabKey()==CreativeModeTabs.COMBAT){
			ITEMS.getEntries().forEach(entry -> event.accept(entry.get()));
		}
	}
}
