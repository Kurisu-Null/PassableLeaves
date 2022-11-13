package kurisu.passableleaves;

import me.lortseam.completeconfig.api.ConfigContainer;
import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigEntry;

@ConfigContainer.Transitive
@ConfigEntries
public class PassableLeavesConfig extends me.lortseam.completeconfig.data.Config implements ConfigContainer {

    public PassableLeavesConfig() {
        super(PassableLeaves.MOD_ID);
    }

    @ConfigEntry.BoundedFloat(min = 0.0F, max = 1.0F)
    @ConfigEntry.Slider
    private float fallingDistanceReductionMultiplicator = 0.5F;
    @ConfigEntry.BoundedFloat(min = 0.0F, max = 1.0F)
    @ConfigEntry.Slider
    private float fallingSpeedReductionMultiplicator = 0.5F;

    @ConfigEntry.BoundedFloat(min = -1.0F, max = 1.0F)
    @ConfigEntry.Slider
    private float slowMultiplicator = -0.2F;
    private boolean fallingEnabled = true;
    private boolean slowEnabled = true;
    private boolean soundEnabled = true;

    private boolean enchantmentEnabled = true;
    private boolean particlesEnabled = true;

    private boolean playerOnlyAffected = false;

    public float getFallingDistanceReductionMultiplicator() {
        return fallingDistanceReductionMultiplicator;
    }

    public float getFallingSpeedReductionMultiplicator() {
        return fallingSpeedReductionMultiplicator;
    }

    public float getSlowMultiplicator() {
        return slowMultiplicator;
    }

    public boolean isSlowEnabled() {
        return slowEnabled;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public boolean isEnchantmentEnabled() {
        return enchantmentEnabled;
    }

    public boolean isFallingEnabled() {
        return fallingEnabled;
    }

    public boolean isParticlesEnabled() {
        return particlesEnabled;
    }

    public boolean isPlayerOnlyAffected() {
        return playerOnlyAffected;
    }
}