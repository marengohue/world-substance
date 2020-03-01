package net.irrelephant.worldsubstance.worldgen

import net.irrelephant.worldsubstance.blocks.BLOCK_ROSEMARY
import net.irrelephant.worldsubstance.blocks.BlockRosemary
import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.Heightmap
import net.minecraft.world.IWorld
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.Feature
import java.util.*

class RosemaryPatchFeature(cfg: FeatureCfg) : Feature<DefaultFeatureConfig>(cfg) {

    companion object {
        const val MIN_PATCH_SIZE = 10
        const val MAX_PATCH_SIZE = 20
        const val CLAY_CHANCE = 0.7f
        const val SPOT_SPREAD = 2
    }

    override fun generate(
        world: IWorld?, generator: ChunkGenerator<out ChunkGeneratorConfig>?,
        random: Random?, pos: BlockPos?, config: DefaultFeatureConfig?
    ): Boolean {
        generateFeature(world!!, random!!, pos!!)
        return true
    }

    private fun generateFeature(world: IWorld, random: Random, featurePos: BlockPos) {
        val spotSize = MIN_PATCH_SIZE + random.nextInt(MAX_PATCH_SIZE)
        (0..spotSize).fold(featurePos, { currentPos, _ ->
            val worldTop = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, currentPos)
            tryPlaceSpot(world, worldTop.down(1), random)
            randomAround(currentPos, SPOT_SPREAD, random)
        })
    }

    private fun tryPlaceSpot(world: IWorld, pos: BlockPos, random: Random) {
        val blockState = world.getBlockState(pos)
        if (BlockRosemary.GROWS_ON.contains(blockState.block)) {
            world.setBlockState(pos, Blocks.COARSE_DIRT.defaultState, 3)
            (-1 downTo -(1 + random.nextInt(3))).forEach { yOffset ->
                if (random.nextFloat() < CLAY_CHANCE) {
                    world.setBlockState(pos.up(yOffset), Blocks.CLAY.defaultState, 3)
                }
            }
            val randomPlant = BLOCK_ROSEMARY.defaultState.with(BlockRosemary.AGE, (0..3).random())
            world.setBlockState(pos.up(1), randomPlant, 3)
        }
    }

    private fun randomAround(pos: BlockPos, range: Int, random: Random): BlockPos {
        return BlockPos(
            pos.x - range + random.nextInt(2 * range),
            pos.y - range + random.nextInt(2 * range),
            pos.z - range + random.nextInt(2 * range)
        )
    }

}