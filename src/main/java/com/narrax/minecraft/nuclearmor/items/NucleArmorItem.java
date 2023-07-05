package com.narrax.minecraft.nuclearmor.items;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.narrax.minecraft.nuclearmor.items.model.NucleArmorModel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class NucleArmorItem extends ArmorItem {

	public NucleArmorItem(NucleArmorMaterial material, ArmorItem.Type armorType) {
		super(material, armorType, new Properties());
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
				switch(type){
					case HELMET:
						if(player.getAirSupply()<player.getMaxAirSupply()){
							player.setAirSupply(player.getMaxAirSupply());
						}
						break;
					case CHESTPLATE:
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

	protected boolean checkDamageType(DamageSource source){
		return
			!source.is(DamageTypeTags.BYPASSES_ARMOR)
			|| source.is(DamageTypeTags.IS_FALL)
			|| source.is(DamageTypeTags.IS_FREEZING)
			|| source.is(DamageTypeTags.IS_DROWNING)
			|| source.is(DamageTypeTags.IS_LIGHTNING)
			|| source.is(DamageTypes.HOT_FLOOR)
			|| source.is(DamageTypes.IN_WALL)
			|| source.is(DamageTypes.FALLING_ANVIL)
			|| source.is(DamageTypes.FALLING_BLOCK)
			|| source.is(DamageTypes.FALLING_STALACTITE);
	}

	public float handleDamage(LivingEntity entity, EquipmentSlot slot, DamageSource source, float amount){
        ItemStack stack = entity.getItemBySlot(slot);
		if(isPowered(stack) && checkDamageType(source)){
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
		return stack.getItem()==this ? type.getSlot() : null;
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
				switch(type){
					case HELMET:
						root = models.bakeLayer(NucleArmorModel.LAYER_HEAD);
						break;
					case CHESTPLATE:
						root = models.bakeLayer(NucleArmorModel.LAYER_CHEST);
						break;
					case LEGGINGS:
						root = models.bakeLayer(NucleArmorModel.LAYER_LEGS);
						break;
					case BOOTS:
						root = models.bakeLayer(NucleArmorModel.LAYER_FEET);
						break;
					default: break;
				}
				return new HumanoidModel<LivingEntity>(root);
			}
		});
	}

	protected void applyBuffs(Player player){
		switch(type){
			case HELMET:
				player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 2000, 0, false, false, false));
				break;
			case CHESTPLATE:
				player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 2000, 0, false, false, false));
				break;
			case LEGGINGS:
				player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2000, 0, false, false, false));
				break;
			default: break;
		}
	}

	protected void removeBuffs(Player player){
		switch(type){
			case HELMET:
				player.removeEffect(MobEffects.NIGHT_VISION);
				break;
			case CHESTPLATE:
				player.removeEffect(MobEffects.DIG_SPEED);
				break;
			case LEGGINGS:
				player.removeEffect(MobEffects.MOVEMENT_SPEED);
				break;
			default: break;
		}
	}

	protected void applyNerfs(Player player){
		switch(type){
			case HELMET:
				player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 2000, 0, false, false, false));
				break;
			case CHESTPLATE:
				player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 2000, 0, false, false, false));
				break;
			case LEGGINGS:
				player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2000, 0, false, false, false));
				break;
			default: break;
		}
	}

	protected void removeNerfs(Player player){
		switch(type){
			case HELMET:
				player.removeEffect(MobEffects.BLINDNESS);
				break;
			case CHESTPLATE:
				player.removeEffect(MobEffects.DIG_SLOWDOWN);
				break;
			case LEGGINGS:
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
