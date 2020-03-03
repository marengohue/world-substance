package net.irrelephant.worldsubstance.alchemy

import net.irrelephant.worldsubstance.items.ITEM_ROSEMARY
import net.minecraft.potion.Potions

fun registerBrewingRecipes() {
    AlchemyRecipeRegistry.registerPotionRecipe(Potions.WATER, ITEM_ROSEMARY, Potions.SLOWNESS)
}