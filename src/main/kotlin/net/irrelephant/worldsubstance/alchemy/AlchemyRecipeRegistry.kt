package net.irrelephant.worldsubstance.alchemy

import net.irrelephant.worldsubstance.alchemy.mixin.BrewingRecipeRegistryAccessor
import net.minecraft.item.Item
import net.minecraft.potion.Potion

object AlchemyRecipeRegistry {

    fun registerPotionRecipe(input: Potion, item: Item, output: Potion) {
        BrewingRecipeRegistryAccessor.registerPotionRecipe(input, item, output)
    }

}

