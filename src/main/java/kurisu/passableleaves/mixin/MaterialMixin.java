package kurisu.passableleaves.mixin;

import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Material.class)
public class MaterialMixin {
    @Mutable
    @Shadow
    @Final
    public static Material LEAVES;

    static {
        Material.Builder builder = new Material.Builder(MapColor.DARK_GREEN);
        builder = ((MaterialBuilderAccess) builder).setBurnable();
        builder = ((MaterialBuilderAccess) builder).setLightPassesThrough();
        builder = ((MaterialBuilderAccess) builder).setDestroyedByPiston();

        LEAVES = builder.notSolid().allowsMovement().build();
    }
}
