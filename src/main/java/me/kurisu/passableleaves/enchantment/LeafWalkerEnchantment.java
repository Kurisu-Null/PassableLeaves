package me.kurisu.passableleaves.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class LeafWalkerEnchantment extends Enchantment {

    protected LeafWalkerEnchantment() {
        super(Rarity.COMMON, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});

        PassableLeavesEnchantments.register("leaf_walker", this);
    }
}
