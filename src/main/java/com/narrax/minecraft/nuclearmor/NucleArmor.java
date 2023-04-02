package com.narrax.minecraft.nuclearmor;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.narrax.minecraft.nuclearmor.items.ModItems;
import com.narrax.minecraft.nuclearmor.items.NucleArmorItem;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NucleArmor.MODID)
public class NucleArmor {
    public static final String MODID = "nuclearmor";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NucleArmor(){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register items
        ModItems.ITEMS.register(modEventBus);
    }

    //Events
    @Mod.EventBusSubscriber(modid = MODID, bus = Bus.FORGE)
    public static class ModEvents{
        @SubscribeEvent
        public static void armorDamageHandler(LivingHurtEvent event){
            for(ItemStack armor : event.getEntity().getArmorSlots()){
                if(armor.getItem() instanceof NucleArmorItem nArmor){
                    LOGGER.info("Damage event fired with chest:" + nArmor.isPowered(armor));
                    event.setAmount(nArmor.handleDamage(event.getEntity(), armor.getEquipmentSlot(), event.getSource(), event.getAmount()));
                }
            }
        }

        @SubscribeEvent
        public static void armorChanged(LivingEquipmentChangeEvent event){
            if(event.getEntity() instanceof Player player){
                if(event.getFrom().getItem() instanceof NucleArmorItem nArmor){
                    nArmor.removeEffects(player);
                }
                if(event.getTo().getItem() instanceof NucleArmorItem nArmor){
                    nArmor.addEffects(player);
                }
            }
        }
    }
}
