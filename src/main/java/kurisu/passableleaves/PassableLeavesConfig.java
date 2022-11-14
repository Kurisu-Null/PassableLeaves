package kurisu.passableleaves;

import lombok.Getter;
import me.lortseam.completeconfig.api.ConfigContainer;
import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.data.Config;

@ConfigEntries
public final class PassableLeavesConfig extends Config implements ConfigContainer {

    public PassableLeavesConfig() {
        super(PassableLeaves.MOD_ID);
    }

    @ConfigEntry.BoundedFloat(min = 0.0F, max = 1.0F)
    @ConfigEntry.Slider
    @Getter
    private static float fallingDistanceReductionMultiplier = 0.5F;

    @ConfigEntry.BoundedFloat(min = 0.0F, max = 1.0F)
    @ConfigEntry.Slider
    @Getter
    private static float fallingSpeedReductionMultiplier = 0.5F;

    @ConfigEntry.BoundedFloat(min = -1.0F, max = 1.0F)
    @ConfigEntry.Slider
    @Getter
    private static float slowMultiplier = -0.2F;

    @Getter
    private static boolean fallingEnabled = true;

    @Getter
    private static boolean slowEnabled = true;

    @Getter
    private static boolean soundEnabled = true;

    @Getter
    private static boolean enchantmentEnabled = true;

    @Getter
    private static boolean particlesEnabled = true;

    @Getter
    private static boolean playerOnlyAffected = false;

    @Getter
    private static boolean walkOnTopOfLeavesEnabled = false;

    @Getter
    private static boolean sprintOnTopOfLeavesEnabled = false;
}