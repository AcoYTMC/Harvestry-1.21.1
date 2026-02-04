package kindling.harvestry.impl.index;

import kindling.harvestry.impl.Harvestry;
import kindling.harvestry.impl.block.entity.WitherRibcgeBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface HarvestryBlockEntities {
    BlockEntityType<WitherRibcgeBlockEntity> WITHER_RIBCAGE = register("wither_ribcage", WitherRibcgeBlockEntity::new, HarvestryBlocks.WITHER_RIBCAGE);

    static void init() {}

    static <T extends BlockEntity> BlockEntityType<T> register(String name,BlockEntityType.BlockEntityFactory<? extends T> entityFactory, Block... blocks) {
        Identifier id = Identifier.of(Harvestry.MOD_ID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, BlockEntityType.Builder.<T>create(entityFactory, blocks).build());
    }
}
