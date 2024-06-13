package com.narrax.minecraft.nuclearmor.items;

import com.narrax.minecraft.nuclearmor.NucleArmor;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = NucleArmor.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NucleArmor.MODID);

	private static final DeferredItem<NucleArmorItem> NUCLEARMOR_HELMET = ITEMS.registerItem(
		"nuclear_helmet", 
		(properties) -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_OTHER, ArmorItem.Type.HELMET, properties),
		new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(20))
	);
	private static final DeferredItem<NucleArmorItem> NUCLEARMOR_CHESTPLATE = ITEMS.registerItem(
		"nuclear_chestplate", 
		(properties) -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_CHEST, ArmorItem.Type.CHESTPLATE, properties),
		new Item.Properties().durability(4800)
	);
	private static final DeferredItem<NucleArmorItem> NUCLEARMOR_LEGGINGS = ITEMS.registerItem(
		"nuclear_leggings", 
		(properties) -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_OTHER, ArmorItem.Type.LEGGINGS, properties),
		new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(20))
	);
	private static final DeferredItem<NucleArmorItem> NUCLEARMOR_BOOTS = ITEMS.registerItem(
		"nuclear_boots", 
		(properties) -> new NucleArmorItem(NucleArmorMaterial.NUCLEAR_MATERIAL_OTHER, ArmorItem.Type.BOOTS, properties),
		new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(20))
	);

	@SubscribeEvent
	public static void creativeTabBuildContents(BuildCreativeModeTabContentsEvent event){
		if(event.getTabKey()==CreativeModeTabs.COMBAT){
			ITEMS.getEntries().forEach(entry -> event.accept(entry.get()));
		}
	}
}
