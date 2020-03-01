package net.irrelephant.worldsubstance.worldgen

import com.mojang.datafixers.Dynamic
import net.irrelephant.worldsubstance.WsId
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biomes
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorConfig
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.FeatureConfig

typealias FeatureCfg = ((t: Dynamic<*>) -> DefaultFeatureConfig)

val FEATURE_ROSEMARY_PATCH = RosemaryPatchFeature { DefaultFeatureConfig.deserialize(it) }

fun <TFeature> registerFeature(feature: TFeature, id: String)
    where TFeature : Feature<DefaultFeatureConfig>
{
    Registry.register( Registry.FEATURE, WsId(id), feature)
}

fun registerWorldgenFeatures() {
    registerFeature(FEATURE_ROSEMARY_PATCH, "feature_rosemary_patch")
}

fun registerFeaturesInBiomes() {
    Registry.BIOME.filter {
        setOf(
            Biomes.RIVER,
            Biomes.PLAINS,
            Biomes.SUNFLOWER_PLAINS,
            Biomes.SAVANNA,
            Biomes.SAVANNA_PLATEAU
        ).contains(it)
    }.forEach { biome ->
        biome.addFeature(
            GenerationStep.Feature.VEGETAL_DECORATION,
            FEATURE_ROSEMARY_PATCH
                .configure(FeatureConfig.DEFAULT)
                .createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(ChanceDecoratorConfig(80)))
        )
    }
}

fun registerWorldgen() {
    registerWorldgenFeatures()
    registerFeaturesInBiomes()
}