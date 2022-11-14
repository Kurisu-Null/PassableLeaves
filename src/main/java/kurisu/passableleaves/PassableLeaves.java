package kurisu.passableleaves;

import kurisu.passableleaves.enchantment.PassableLeavesEnchantments;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;

public class PassableLeaves implements ModInitializer {
    public static final String MOD_ID = "passableleaves";

    @Override
    public void onInitialize() {
       new PassableLeavesConfig().load();

        PassableLeavesEnchantments.initialize();
    }
    
    public static boolean isFlyingInCreative(PlayerEntity playerEntity) {
        return playerEntity.getAbilities().creativeMode && playerEntity.getAbilities().flying;
    }
}
