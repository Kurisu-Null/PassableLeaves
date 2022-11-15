package me.kurisu.passableleaves;

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
    public static float fallingDistanceReductionMultiplier = 0.5F;

    @ConfigEntry.BoundedFloat(min = 0.0F, max = 1.0F)
    @ConfigEntry.Slider
    public static float fallingSpeedReductionMultiplier = 0.5F;

    @ConfigEntry.BoundedFloat(min = -1.0F, max = 1.0F)
    @ConfigEntry.Slider
    public static float slowMultiplier = -0.2F;

    public static boolean fallingEnabled = true;

    public static boolean slowEnabled = true;

    public static boolean soundEnabled = true;

    public static boolean enchantmentEnabled = true;

    public static boolean particlesEnabled = true;

    public static boolean playerOnlyAffected = false;

    public static boolean walkOnTopOfLeavesEnabled = false;

    public static boolean sprintOnTopOfLeavesEnabled = false;

    public static boolean fallWhenSneakingEnabled = true;
}