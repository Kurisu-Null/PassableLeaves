package me.kurisu.passableleaves.mixin;

import me.kurisu.passableleaves.access.PlayerEntityAccess;
import me.kurisu.passableleaves.enums.KeybindAction;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashSet;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements PlayerEntityAccess {
    @Unique
    private final HashSet<KeybindAction> keybindActions = new HashSet();

    @Override
    public void addKeybindAction(KeybindAction keybindAction) {
        keybindActions.add(keybindAction);
    }

    @Override
    public boolean hasKeybindAction(KeybindAction keybindAction) {
        return keybindActions.contains(keybindAction);
    }

    @Override
    public void removeKeybindActions(KeybindAction keybindAction) {
        keybindActions.remove(keybindAction);
    }
}
