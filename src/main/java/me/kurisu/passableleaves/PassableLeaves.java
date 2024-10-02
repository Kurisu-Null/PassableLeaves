package me.kurisu.passableleaves;

import io.wispforest.owo.network.OwoNetChannel;
import me.kurisu.passableleaves.enchantment.PassableLeavesEnchantments;
import me.kurisu.passableleaves.network.NetworkHandlerHitLeaves;
import me.kurisu.passableleaves.network.NetworkHandlerKeyInput;
import me.kurisu.passableleaves.network.NetworkHandlerSound;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PassableLeaves implements ModInitializer {
    public static final String MOD_ID = "passableleaves";
    public static final me.kurisu.passableleaves.PassableLeavesConfig CONFIG = me.kurisu.passableleaves.PassableLeavesConfig.createAndLoad();
    public static final OwoNetChannel PASSABLE_LEAVES_CHANNEL = OwoNetChannel.create(new Identifier("passable_leaves", "main"));
    public static final Logger LOGGER = LogManager.getLogger("passable_leaves");

    @Override
    public void onInitialize() {
        PassableLeavesEnchantments.initialize();
        NetworkHandlerKeyInput.registerServer();
        NetworkHandlerSound.registerServer();
        NetworkHandlerHitLeaves.registerServer();
    }

    public static boolean isFlyingInCreative(PlayerEntity playerEntity) {
        return playerEntity.getAbilities().creativeMode && playerEntity.getAbilities().flying;
    }
}
