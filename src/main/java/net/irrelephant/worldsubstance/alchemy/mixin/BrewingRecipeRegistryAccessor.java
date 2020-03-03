package net.irrelephant.worldsubstance.alchemy.mixin;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@SuppressWarnings("PublicStaticMixinMember")
@Mixin(BrewingRecipeRegistry.class)
public interface BrewingRecipeRegistryAccessor  {

    @Invoker("registerPotionType")
    static void registerPotionType(Item item) {
        throw new UnsupportedOperationException();
    }

    @Invoker("registerItemRecipe")
    static void registerItemRecipe(Item potionItem, Item modifier, Item resultPotionItem) {
        throw new UnsupportedOperationException();
    }

    @Invoker("registerPotionRecipe")
    static void registerPotionRecipe(Potion input, Item modifier, Potion output) {
        throw new UnsupportedOperationException();
    }

    @Accessor("POTION_TYPES")
    static List<Ingredient> getPotionTypes() {
        throw new UnsupportedOperationException();
    }
}