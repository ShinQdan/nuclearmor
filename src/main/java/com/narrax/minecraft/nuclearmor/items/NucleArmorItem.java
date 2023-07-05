package com.narrax.minecraft.nuclearmor.items;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.narrax.minecraft.nuclearmor.items.model.NucleArmorModel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class NucleArmorItem extends ArmorItem {

	public NucleArmorItem(NucleArmorMaterial material, EquipmentSlot slot) {
		super(material, slot, new Properties().tab(CreativeModeTab.TAB_COMBAT));
	}

	public boolean isPowerSource(ItemStack stack){
		return stack.getItem() instanceof NucleArmorItem nArmor && nArmor.material instanceof NucleArmorMaterial nMaterial && nMaterial.powerSource;
	}

	public boolean isPowered(ItemStack stack){
		if(isPowerSource(stack)){
			return stack.getDamageValue() < stack.getMaxDamage()-1;
		}else return false;
	}

	public int powerLevel(Player player){
		ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
		if(!isPowered(chest)) return 0;
		if(chest.getDamageValue()<chest.getMaxDamage()*8/10) return 2;
		else if(chest.getDamageValue()<chest.getMaxDamage()*9/10) return 1;
		else return 0;
	}
	
	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player) {
		if(!level.isClientSide() && level.getGameTime()%20==0){
			int power = powerLevel(player);
			if(power>1){
				removeNerfs(player);
				applyBuffs(player);
				switch(slot){
					case HEAD:
						if(player.getAirSupply()<player.getMaxAirSupply()){
							player.setAirSupply(player.getMaxAirSupply());
						}
						break;
					case CHEST:
						if(stack.getDamageValue()<stack.getMaxDamage()-1){
							stack.setDamageValue(stack.getDamageValue()+1);
						}
						break;
					default: break;
				}
			}else if(power<1){
				removeBuffs(player);
				applyNerfs(player);
			}
		}
	}

	public float handleDamage(LivingEntity entity, EquipmentSlot slot, DamageSource source, float amount){
        ItemStack stack = entity.getItemBySlot(slot);
		if(
			isPowered(stack) && (!source.isBypassArmor() || source.isFall() || source==DamageSource.FREEZE)
		){
			for(ItemStack aStack : entity.getArmorSlots()){
				//if entity does not have a full set the damage is not reduced.
				if(!(aStack.getItem() instanceof NucleArmorItem)) return amount;
			}
			int power = stack.getDamageValue();
			int max = stack.getMaxDamage()-1;
			if(power+amount < max){
				stack.setDamageValue((int)(power+amount));
				return 0;
			}else{
				stack.setDamageValue(max);
				return power+amount-max;
			}
		}
		return amount;
    }

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
		if(isPowerSource(stack) && stack.getDamageValue()+amount >= stack.getMaxDamage()-1){
			return 0;
		}else return super.damageItem(stack, amount, entity, onBroken);
	}

	public void addEffects(Player player){
		int power = powerLevel(player);
		if(power>1){
			removeNerfs(player);
			applyBuffs(player);
		}else if(power<1){
			removeBuffs(player);
			applyNerfs(player);
		}
	}

	@Override
	public @Nullable EquipmentSlot getEquipmentSlot(ItemStack stack){
		return stack.getItem()==this ? slot : null;
	}

	@Override
	public boolean isRepairable(ItemStack stack) {
		return true;
	}

	@Override
	public boolean isValidRepairItem(ItemStack tool, ItemStack material){
		if(tool.getItem() instanceof NucleArmorItem nArmor && nArmor.getMaterial() instanceof NucleArmorMaterial nMaterial){
			return material.is(nMaterial.getRepairTagKey());
		}
		return false;
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
				EntityModelSet models = Minecraft.getInstance().getEntityModels();
				ModelPart root = null;
				switch(slot){
					case HEAD:
						root = models.bakeLayer(NucleArmorModel.LAYER_HEAD);
						break;
					case CHEST:
						root = models.bakeLayer(NucleArmorModel.LAYER_CHEST);
						break;
					case LEGS:
						root = models.bakeLayer(NucleArmorModel.LAYER_LEGS);
						break;
					case FEET:
						root = models.bakeLayer(NucleArmorModel.LAYER_FEET);
						break;
					default: break;
				}
				return new HumanoidModel<LivingEntity>(root);
			}
		});
	}

	protected void applyBuffs(Player player){
		switch(slot){
			case HEAD:
				player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 2000, 0, false, false, false));
				break;
			case CHEST:
				player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 2000, 0, false, false, false));
				break;
			case LEGS:
				player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2000, 0, false, false, false));
				break;
			default: break;
		}
	}

	protected void removeBuffs(Player player){
		switch(slot){
			case HEAD:
				player.removeEffect(MobEffects.NIGHT_VISION);
				break;
			case CHEST:
				player.removeEffect(MobEffects.DIG_SPEED);
				break;
			case LEGS:
				player.removeEffect(MobEffects.MOVEMENT_SPEED);
				break;
			default: break;
		}
	}

	protected void applyNerfs(Player player){
		switch(slot){
			case HEAD:
				player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 2000, 0, false, false, false));
				break;
			case CHEST:
				player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 2000, 0, false, false, false));
				break;
			case LEGS:
				player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2000, 0, false, false, false));
				break;
			default: break;
		}
	}

	protected void removeNerfs(Player player){
		switch(slot){
			case HEAD:
				player.removeEffect(MobEffects.BLINDNESS);
				break;
			case CHEST:
				player.removeEffect(MobEffects.DIG_SLOWDOWN);
				break;
			case LEGS:
				player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
				break;
			default: break;
		}
	}

	public void removeEffects(Player player){
		removeBuffs(player);
		removeNerfs(player);
	}
}
