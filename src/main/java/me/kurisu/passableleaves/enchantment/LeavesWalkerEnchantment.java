package me.kurisu.passableleaves.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class LeavesWalkerEnchantment extends Enchantment {
    protected LeavesWalkerEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});

        PassableLeavesEnchantments.register("leaves_walker", this);
    }

    // Define how many levels this enchantment has (e.g., 3 levels)
    @Override
    public int getMaxLevel() {
        return 3;
    }

    // This enchantment can be applied to boots
    @Override
    public boolean isTreasure() {
        return false; // Not a treasure enchantment (so it's available in the Enchantment Table)
    }

    // Make it available in the Enchantment Table for boots
    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return true; // Available as an enchanted book
    }

    // Make this enchantment selectable in the Enchantment Table
    @Override
    public boolean isAvailableForRandomSelection() {
        return true; // Can be randomly selected in the enchantment table
    }
}
