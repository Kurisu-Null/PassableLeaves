package me.kurisu.passableleaves;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

@Modmenu(modId = PassableLeaves.MOD_ID)
@Config(name = "passableleaves", wrapperName = "PassableLeavesConfig")
public class PassableLeavesConfigModel {
    @RangeConstraint(min = 0.0F, max = 1.0F)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public float fallingDistanceReductionMultiplier = 0.5F;

    @RangeConstraint(min = 0.0F, max = 1.0F)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public float fallingSpeedReductionMultiplier = 0.5F;

    @RangeConstraint(min = -1.0F, max = 1.0F)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public float slowMultiplier = -0.2F;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean fallingEnabled = true;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean slowEnabled = true;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    @RestartRequired
    public boolean enchantmentEnabled = true;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean playerOnlyAffected = false;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean walkOnTopOfLeavesEnabled = false;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean sprintOnTopOfLeavesEnabled = false;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean fallWhenSneakingEnabled = true;

    public boolean soundEnabled = true;
    public boolean particlesEnabled = true;
}