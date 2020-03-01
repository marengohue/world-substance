package net.irrelephant.worldsubstance

import net.irrelephant.worldsubstance.blocks.registerBlocks
import net.irrelephant.worldsubstance.items.registerItems
import net.irrelephant.worldsubstance.worldgen.registerWorldgen

class WorldSubstance {

    @Suppress("unused")
    fun init() {
        println("Hello Fabric world!")
        registerItems()
        registerBlocks()
        registerWorldgen()
    }

}