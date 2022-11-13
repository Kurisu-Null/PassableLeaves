package kurisu.passableleaves;

import kurisu.passableleaves.enchantment.PassableLeavesEnchantments;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;

public class PassableLeaves implements ModInitializer {
    public static final String MOD_ID = "passableleaves";
    private static PassableLeavesConfig config;

    @Override
    public void onInitialize() {
        config = new PassableLeavesConfig();
        config.load();

        new PassableLeavesEnchantments();
    }

    public static PassableLeavesConfig getConfig() {
        return config;
    }

    public static boolean isFlyingInCreative(PlayerEntity playerEntity) {
        return
                playerEntity.getAbilities().creativeMode && playerEntity.getAbilities().flying;
    }
}
