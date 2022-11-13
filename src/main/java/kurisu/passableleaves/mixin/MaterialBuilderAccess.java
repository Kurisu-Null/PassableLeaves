package kurisu.passableleaves.mixin;

import net.minecraft.block.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Material.Builder.class)
public interface MaterialBuilderAccess {
    @Invoker("burnable")
    public Material.Builder setBurnable();

    @Invoker("lightPassesThrough")
    public Material.Builder setLightPassesThrough();

    @Invoker("destroyedByPiston")
    public Material.Builder setDestroyedByPiston();
}
