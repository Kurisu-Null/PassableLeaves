package me.kurisu.passableleaves;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

@Modmenu(modId = PassableLeaves.MOD_ID)
@Config(name = "passableleaves", wrapperName = "PassableLeavesConfig")
public class PassableLeavesConfigModel {

    @RangeConstraint(min = 0.0F, max = 1.0F)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public float fallingDistanceMultiplier = 0.5F;

    @RangeConstraint(min = 0.0F, max = 1.0F)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public float fallingSpeedMultiplier = 0.5F;

    @RangeConstraint(min = 0.0F, max = 1.0F)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public float walkSlowMultiplier = 0.8F;

    @RangeConstraint(min = 0.0F, max = 1.0F)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public float jumpSlowMultiplier = 0.8F;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean fallingEnabled = true;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    @RestartRequired
    public boolean enchantmentEnabled = true;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean playerOnly = false;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean leafWalking = false;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean leafSprinting = false;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean fallOnKeyPress = true;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean disableFovChange = false;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean soundEnabled = true;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean hideNameTag = false;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean particlesEnabled = true;

    @RangeConstraint(min = 0.0F, max = 1.0F)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public float projectileHitLeavesChance = 0.5F;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean canHitThroughLeaves = true;
}