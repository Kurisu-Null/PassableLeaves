package me.kurisu.passableleaves.enchantment;

import me.kurisu.passableleaves.PassableLeaves;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

public class PassableLeavesEnchantments {
    public static Enchantment LEAVES_WALKER;
    public static Enchantment LEAVES_STRIDER;

    public static void initialize() {
        if (PassableLeaves.CONFIG.enchantmentEnabled()) {
            LEAVES_WALKER = new LeavesWalkerEnchantment();
            LEAVES_STRIDER = new LeavesStriderEntchantment();
        }
    }

    public static void register(String name, Enchantment enchantment) {
        Registry.register(Registries.ENCHANTMENT, new Identifier(PassableLeaves.MOD_ID, name), enchantment);
    }
}
