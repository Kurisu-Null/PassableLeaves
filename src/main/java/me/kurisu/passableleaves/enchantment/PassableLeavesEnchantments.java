package me.kurisu.passableleaves.enchantment;

import me.kurisu.passableleaves.PassableLeaves;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PassableLeavesEnchantments {
    public static Enchantment LEAF_WALKER;

    public static void initialize() {
        if (PassableLeaves.CONFIG.enchantmentEnabled()) {
            LEAF_WALKER = new LeafWalkerEnchantment();
        }
    }

    public static void register(String name, Enchantment enchantment) {
        Registry.register(Registry.ENCHANTMENT, new Identifier(PassableLeaves.MOD_ID, name), enchantment);
    }
}
