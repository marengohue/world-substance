package net.irrelephant.worldsubstance.blocks

import net.irrelephant.worldsubstance.items.ITEM_ROSEMARY
import net.minecraft.block.*
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.IntProperty
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.*
import java.util.function.Consumer

class BlockRosemary(settings: Settings) : PlantBlock(settings) {

    init {
        defaultState = stateManager.defaultState
            .with(IS_WILD, true)
            .with(AGE, 0)
    }

    companion object {
        const val MAX_AGE = 3
        const val TICK_RATE = 10
        val AGE: IntProperty = IntProperty.of("age", 0, MAX_AGE)
        val IS_WILD: BooleanProperty = BooleanProperty.of("wild")
        val GROWS_ON = setOf<Block>(
            Blocks.PODZOL,
            Blocks.DIRT,
            Blocks.COARSE_DIRT,
            Blocks.GRASS_BLOCK
        )
    }

    override fun isTranslucent(state: BlockState?, world: BlockView?, pos: BlockPos?): Boolean {
        return true
    }

    override fun canPlantOnTop(floor: BlockState?, world: BlockView?, pos: BlockPos?): Boolean {
        return floor != null && GROWS_ON.contains(floor.block)
    }

    override fun onUse(
        state: BlockState?, world: World, pos: BlockPos?,
        player: PlayerEntity?, hand: Hand?, hit: BlockHitResult?
    ): ActionResult? {
        if (player != null && pos != null && state !== null && canShear(state)) {
            val heldStack = player.getStackInHand(hand)
            if (heldStack.item == Items.SHEARS) {
                shearHerb(player, world, heldStack, hand, pos, state)
                return ActionResult.SUCCESS
            }
        }

        return ActionResult.PASS
    }

    private fun shearHerb(
        player: PlayerEntity,
        world: World,
        heldStack: ItemStack,
        hand: Hand?,
        pos: BlockPos,
        state: BlockState
    ) {
        player.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0f, 1.0f)
        if (!world.isClient) {
            damageShears(heldStack, player, hand)
            dropItems(world, pos)
            updateStateAfterShear(world, pos, state)
        }
    }

    private fun damageShears(heldStack: ItemStack, player: PlayerEntity?, hand: Hand? ) {
        heldStack.damage(1, player,
            Consumer { playerEntity: PlayerEntity ->
                playerEntity.sendToolBreakStatus(hand)
            }
        )
    }

    private fun canShear(state: BlockState): Boolean {
        return state.get(AGE) as Int == MAX_AGE
    }

    private fun updateStateAfterShear(world: World, pos: BlockPos, state: BlockState) {
        val currentAge = state.get(AGE) as Int
        val reduceBy = 1 + world.random.nextInt(3)
        world.setBlockState(pos, state.with(AGE, currentAge - reduceBy), 2)
    }

    private fun dropItems(world: World, pos: BlockPos) {
        val itemEntity: ItemEntity = dropStack(world, ItemStack(ITEM_ROSEMARY), pos)
        itemEntity.velocity = itemEntity.velocity.add(
            ((world.random.nextFloat() - world.random.nextFloat()) * 0.1f).toDouble(),
            (world.random.nextFloat() * 0.05f).toDouble(),
            ((world.random.nextFloat() - world.random.nextFloat()) * 0.1f).toDouble()
        )
    }

    private fun dropStack(world: World, stack: ItemStack, pos: BlockPos): ItemEntity {
        val itemEntity = ItemEntity(world, pos.x.toDouble() + 0.5, pos.y.toDouble() + 1, pos.z.toDouble() + 0.5, stack)
        itemEntity.setToDefaultPickupDelay()
        world.spawnEntity(itemEntity)
        return itemEntity
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder!!
            .add(AGE)
            .add(IS_WILD)
    }

    override fun scheduledTick(state: BlockState?, world: ServerWorld?, pos: BlockPos?, random: Random?) {
        val currentAge = state!!.get(AGE) as Int
        if (currentAge < MAX_AGE && random!!.nextInt(TICK_RATE) == 0) {
            val nextState = state.with(NetherWartBlock.AGE, currentAge + 1)
            world!!.setBlockState(pos, nextState, 2)
        }

        super.scheduledTick(state, world, pos, random)
    }
}