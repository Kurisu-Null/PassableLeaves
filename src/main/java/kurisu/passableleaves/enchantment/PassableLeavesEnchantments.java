package kurisu.passableleaves.enchantment;

import kurisu.passableleaves.PassableLeaves;
import kurisu.passableleaves.PassableLeavesConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PassableLeavesEnchantments {
    private static final PassableLeavesConfig config = PassableLeaves.getConfig();
    public static Enchantment LEAF_WALKER;

    public PassableLeavesEnchantments() {
        if (config.isEnchantmentEnabled()) {
            LEAF_WALKER = new LeafWalkerEnchantment();
        }
    }

    public static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, new Identifier(PassableLeaves.MOD_ID, name), enchantment);
    }

}
