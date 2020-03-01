package net.irrelephant.worldsubstance.blocks

import net.fabricmc.fabric.api.block.FabricBlockSettings
import net.irrelephant.worldsubstance.WsId
import net.minecraft.block.Material
import net.minecraft.block.MaterialColor
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.registry.Registry

val BLOCK_ROSEMARY = BlockRosemary(
    FabricBlockSettings
        .of(Material.PLANT)
        .materialColor(MaterialColor.EMERALD)
        .breakInstantly()
        .noCollision()
        .nonOpaque()
        .sounds(BlockSoundGroup.GRASS)
        .ticksRandomly()
        .build()
)

fun registerBlocks() {
    Registry.register(Registry.BLOCK, WsId("plant_rosemary"), BLOCK_ROSEMARY)
}