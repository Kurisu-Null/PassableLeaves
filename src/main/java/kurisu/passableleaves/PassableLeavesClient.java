package kurisu.passableleaves;

import me.lortseam.completeconfig.gui.ConfigScreenBuilder;
import me.lortseam.completeconfig.gui.coat.CoatScreenBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class PassableLeavesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigScreenBuilder.setMain(PassableLeaves.MOD_ID, new CoatScreenBuilder());
    }

}
