package net.irrelephant.worldsubstance

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.irrelephant.worldsubstance.blocks.BLOCK_ROSEMARY
import net.minecraft.client.render.RenderLayer

@Environment(EnvType.CLIENT)

class WorldSubstanceClient : ClientModInitializer {

    override fun onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BLOCK_ROSEMARY, RenderLayer.getCutout())
    }

}