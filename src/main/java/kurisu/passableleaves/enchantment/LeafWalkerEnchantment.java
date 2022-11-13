package kurisu.passableleaves.enchantment;

import kurisu.passableleaves.PassableLeaves;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class LeafWalkerEnchantment extends Enchantment {

    protected LeafWalkerEnchantment() {
        super(Rarity.COMMON, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});

        PassableLeavesEnchantments.register("leaf_walker", this);
    }
}
