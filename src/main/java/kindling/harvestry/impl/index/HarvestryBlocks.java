package kindling.harvestry.impl.index;

import kindling.harvestry.impl.Harvestry;
import kindling.harvestry.impl.block.WitherRibcageBlock;
import net.acoyt.acornlib.impl.item.TranslationBlockItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.Function;

import static kindling.harvestry.impl.Harvestry.HARVESTRY_MAIN_KEY;

public interface HarvestryBlocks {
    Block WITHER_RIBCAGE = createWithItem("wither_ribcage", WitherRibcageBlock::new, Settings.copy(Blocks.BONE_BLOCK));

    Block COMPACTED_STRANGE_SILK = createWithItem("compacted_strange_silk", Block::new, Settings.copy(Blocks.WHITE_WOOL));
    Block BLACK_COMPACTED_STRANGE_SILK = createWithItem("black_compacted_strange_silk", Block::new, Settings.copy(Blocks.BLACK_WOOL));
    Block PURPLE_COMPACTED_STRANGE_SILK = createWithItem("purple_compacted_strange_silk", Block::new, Settings.copy(Blocks.PURPLE_WOOL));
    Block LIGHT_BLUE_COMPACTED_STRANGE_SILK = createWithItem("light_blue_compacted_strange_silk", Block::new, Settings.copy(Blocks.LIGHT_BLUE_WOOL));
    Block PINK_COMPACTED_STRANGE_SILK = createWithItem("pink_compacted_strange_silk", Block::new, Settings.copy(Blocks.PINK_WOOL));
    Block WHITE_COMPACTED_STRANGE_SILK = createWithItem("white_compacted_strange_silk", Block::new, Settings.copy(Blocks.WHITE_WOOL));
    Block MAGENTA_COMPACTED_STRANGE_SILK = createWithItem("magenta_compacted_strange_silk", Block::new, Settings.copy(Blocks.MAGENTA_WOOL));
    Block BLUE_COMPACTED_STRANGE_SILK = createWithItem("blue_compacted_strange_silk", Block::new, Settings.copy(Blocks.BLUE_WOOL));
    Block ORANGE_COMPACTED_STRANGE_SILK = createWithItem("orange_compacted_strange_silk", Block::new, Settings.copy(Blocks.ORANGE_WOOL));
    Block LIME_COMPACTED_STRANGE_SILK = createWithItem("lime_compacted_strange_silk", Block::new, Settings.copy(Blocks.LIME_WOOL));
    Block RED_COMPACTED_STRANGE_SILK = createWithItem("red_compacted_strange_silk", Block::new, Settings.copy(Blocks.RED_WOOL));
    Block LIGHT_GRAY_COMPACTED_STRANGE_SILK = createWithItem("light_gray_compacted_strange_silk", Block::new, Settings.copy(Blocks.LIGHT_GRAY_WOOL));
    Block YELLOW_COMPACTED_STRANGE_SILK = createWithItem("yellow_compacted_strange_silk", Block::new, Settings.copy(Blocks.YELLOW_WOOL));
    Block BROWN_COMPACTED_STRANGE_SILK = createWithItem("brown_compacted_strange_silk", Block::new, Settings.copy(Blocks.BROWN_WOOL));
    Block GREEN_COMPACTED_STRANGE_SILK = createWithItem("green_compacted_strange_silk", Block::new, Settings.copy(Blocks.GREEN_WOOL));
    Block DARK_GRAY_COMPACTED_STRANGE_SILK = createWithItem("dark_gray_compacted_strange_silk", Block::new, Settings.copy(Blocks.GRAY_WOOL));
    Block CYAN_COMPACTED_STRANGE_SILK = createWithItem("cyan_compacted_strange_silk", Block::new, Settings.copy(Blocks.CYAN_WOOL));

    static Block create(String name, Function<Settings, Block> factory, Settings settings) {
        Block block = factory.apply(settings);
        return Registry.register(Registries.BLOCK, Harvestry.id(name), block);
    }

    static Block createWithItem(String name, Function<Settings, Block> factory, Settings settings) {
        Block block = create(name, factory, settings);
        HarvestryItems.create(name, itemSettings -> new TranslationBlockItem(block, itemSettings), new Item.Settings());
        return block;
    }

    static Block createWithItem(String name, Function<Settings, Block> factory, Settings settings, Item.Settings itemSetting) {
        Block block = create(name, factory, settings);
        HarvestryItems.create(name, itemSettings -> new TranslationBlockItem(block, itemSettings), itemSetting);
        return block;
    }

    static void init() {
        ItemGroupEvents.modifyEntriesEvent(HARVESTRY_MAIN_KEY).register(entries -> {
            entries.add(WITHER_RIBCAGE.asItem());
            entries.add(COMPACTED_STRANGE_SILK.asItem());
            entries.add(BLACK_COMPACTED_STRANGE_SILK.asItem());
            entries.add(PURPLE_COMPACTED_STRANGE_SILK.asItem());
            entries.add(LIGHT_BLUE_COMPACTED_STRANGE_SILK.asItem());
            entries.add(PINK_COMPACTED_STRANGE_SILK.asItem());
            entries.add(WHITE_COMPACTED_STRANGE_SILK.asItem());
            entries.add(MAGENTA_COMPACTED_STRANGE_SILK.asItem());
            entries.add(BLUE_COMPACTED_STRANGE_SILK.asItem());
            entries.add(ORANGE_COMPACTED_STRANGE_SILK.asItem());
            entries.add(LIME_COMPACTED_STRANGE_SILK.asItem());
            entries.add(RED_COMPACTED_STRANGE_SILK.asItem());
            entries.add(LIGHT_GRAY_COMPACTED_STRANGE_SILK.asItem());
            entries.add(YELLOW_COMPACTED_STRANGE_SILK.asItem());
            entries.add(BROWN_COMPACTED_STRANGE_SILK.asItem());
            entries.add(GREEN_COMPACTED_STRANGE_SILK.asItem());
            entries.add(DARK_GRAY_COMPACTED_STRANGE_SILK.asItem());
            entries.add(CYAN_COMPACTED_STRANGE_SILK.asItem());
        });
    }

    static void clientInit() {}
}
