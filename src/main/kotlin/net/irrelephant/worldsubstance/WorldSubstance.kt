package net.irrelephant.worldsubstance

import net.irrelephant.worldsubstance.alchemy.registerBrewingRecipes
import net.irrelephant.worldsubstance.blocks.registerBlocks
import net.irrelephant.worldsubstance.items.registerItems
import net.irrelephant.worldsubstance.worldgen.registerWorldgen
import java.rmi.registry.Registry

class WorldSubstance {

    @Suppress("unused")
    fun init() {
        println("Hello Fabric world!")
        registerItems()
        registerBlocks()
        registerWorldgen()
        registerBrewingRecipes()
    }

}