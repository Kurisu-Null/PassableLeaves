package me.kurisu.passableleaves;

import me.kurisu.passableleaves.enchantment.PassableLeavesEnchantments;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;

public class PassableLeaves implements ModInitializer {
    public static final String MOD_ID = "passableleaves";
    public static final me.kurisu.passableleaves.PassableLeavesConfig CONFIG = me.kurisu.passableleaves.PassableLeavesConfig.createAndLoad();

    @Override
    public void onInitialize() {
        PassableLeavesEnchantments.initialize();
    }

    public static boolean isFlyingInCreative(PlayerEntity playerEntity) {
        return playerEntity.getAbilities().creativeMode && playerEntity.getAbilities().flying;
    }
}
