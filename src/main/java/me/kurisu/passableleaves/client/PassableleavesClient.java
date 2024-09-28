package me.kurisu.passableleaves.client;

import me.kurisu.passableleaves.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class PassableleavesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyInputHandler.registerClient();
    }
}
