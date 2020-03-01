package net.irrelephant.worldsubstance.items

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.irrelephant.worldsubstance.WsId
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry


val GROUP_HERBS: ItemGroup = FabricItemGroupBuilder.create(WsId("group_herbs"))
    .icon { -> ItemStack(ITEM_ROSEMARY) }
    .build()

val ITEM_ROSEMARY = ItemHerb(Item.Settings()
    .group(GROUP_HERBS)
    .rarity(Rarity.UNCOMMON)
)

fun registerItems() {
    Registry.register(Registry.ITEM, WsId("herb_rosemary"), ITEM_ROSEMARY)
}