package kindling.harvestry.impl;

import kindling.harvestry.impl.index.HarvestryBlockEntities;
import kindling.harvestry.impl.index.HarvestryBlocks;
import kindling.harvestry.impl.render.WitherRibcageBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

@Environment(EnvType.CLIENT)
public class HarvestryClient implements ClientModInitializer {
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(HarvestryBlockEntities.WITHER_RIBCAGE, WitherRibcageBlockEntityRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(HarvestryBlocks.WITHER_RIBCAGE, RenderLayer.getCutout());
    }
}
