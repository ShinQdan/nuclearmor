package com.narrax.minecraft.nuclearmor;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.narrax.minecraft.nuclearmor.items.ModItems;
import com.narrax.minecraft.nuclearmor.items.NucleArmorItem;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NucleArmor.MODID)
public class NucleArmor {
    public static final String MODID = "nuclearmor";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NucleArmor(IEventBus modEventBus){
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
                    // LOGGER.info("Damage event fired with chest:" + nArmor.isPowered(armor));
                    float dmg = nArmor.handleDamage(event.getEntity(), armor.getEquipmentSlot(), event.getSource(), event.getAmount());
                    // LOGGER.info("Damage left: "+dmg);
                    event.setAmount(dmg);
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
