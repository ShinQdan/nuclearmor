package com.narrax.minecraft.nuclearmor.items;

import com.narrax.minecraft.nuclearmor.NucleArmor;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod.EventBusSubscriber(modid = NucleArmor.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, NucleArmor.MODID);

	private static final DeferredHolder<Item, NucleArmorItem> NUCLEARMOR_HELMET = ITEMS.register("nuclear_helmet", () -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_OTHER, ArmorItem.Type.HELMET));
	private static final DeferredHolder<Item, NucleArmorItem> NUCLEARMOR_CHESTPLATE = ITEMS.register("nuclear_chestplate", () -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_CHEST, ArmorItem.Type.CHESTPLATE));
	private static final DeferredHolder<Item, NucleArmorItem> NUCLEARMOR_LEGGINGS = ITEMS.register("nuclear_leggings", () -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_OTHER, ArmorItem.Type.LEGGINGS));
	private static final DeferredHolder<Item, NucleArmorItem> NUCLEARMOR_BOOTS = ITEMS.register("nuclear_boots", () -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_OTHER, ArmorItem.Type.BOOTS));

	@SubscribeEvent
	public static void creativeTabBuildContents(BuildCreativeModeTabContentsEvent event){
		if(event.getTabKey()==CreativeModeTabs.COMBAT){
			ITEMS.getEntries().forEach(entry -> event.accept(entry.get()));
		}
	}
}
