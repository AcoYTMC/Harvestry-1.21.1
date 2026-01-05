package kindling.harvestry.impl.index;

import kindling.harvestry.impl.Harvestry;
import kindling.harvestry.impl.component.SingleSlotComponent;
import kindling.harvestry.impl.item.ShearDaggerItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.Function;

import static kindling.harvestry.impl.Harvestry.HARVESTRY_MAIN_KEY;

public interface HarvestryItems {
    Item ALCHEMICAL_DAGGER = create(
            "alchemical_dagger",
            settings -> new ShearDaggerItem(ShearDaggerItem.Type.ALCHEMICAL, settings),
            new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 0, -2.0F))
                    .maxCount(1)
                    .component(HarvestryComponents.SINGLE_SLOT, new SingleSlotComponent(ShearDaggerItem.Type.NONE)));

    Item BOTANICAL_DAGGER = create(
            "botanical_dagger",
            settings -> new ShearDaggerItem(ShearDaggerItem.Type.BOTANICAL, settings),
            new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 0, -2.0F))
                    .maxCount(1)
                    .component(HarvestryComponents.SINGLE_SLOT, new SingleSlotComponent(ShearDaggerItem.Type.NONE)));

    Item CANNIBAL_DAGGER = create(
            "cannibal_dagger",
            settings -> new ShearDaggerItem(ShearDaggerItem.Type.CANNIBAL, settings),
            new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 0, -2.0F))
                    .maxCount(1)
                    .component(HarvestryComponents.SINGLE_SLOT, new SingleSlotComponent(ShearDaggerItem.Type.NONE)));

    Item WEIRD_WAX = create("weird_wax", Item::new, new Item.Settings());
    Item CURSED_FLESH = create("cursed_flesh", Item::new, new Item.Settings());
    Item BAFFLING_CRYSTALS = create("baffling_crystals", Item::new, new Item.Settings());

    Item STRANGE_SILK = create("strange_silk", Item::new, new Item.Settings());
    Item BLACK_STRANGE_SILK = create("black_strange_silk", Item::new, new Item.Settings());
    Item PURPLE_STRANGE_SILK = create("purple_strange_silk", Item::new, new Item.Settings());
    Item LIGHT_BLUE_STRANGE_SILK = create("light_blue_strange_silk", Item::new, new Item.Settings());
    Item PINK_STRANGE_SILK = create("pink_strange_silk", Item::new, new Item.Settings());
    Item WHITE_STRANGE_SILK = create("white_strange_silk", Item::new, new Item.Settings());
    Item MAGENTA_STRANGE_SILK = create("magenta_strange_silk", Item::new, new Item.Settings());
    Item BLUE_STRANGE_SILK = create("blue_strange_silk", Item::new, new Item.Settings());
    Item ORANGE_STRANGE_SILK = create("orange_strange_silk", Item::new, new Item.Settings());
    Item LIME_STRANGE_SILK = create("lime_strange_silk", Item::new, new Item.Settings());
    Item RED_STRANGE_SILK = create("red_strange_silk", Item::new, new Item.Settings());
    Item LIGHT_GRAY_STRANGE_SILK = create("light_gray_strange_silk", Item::new, new Item.Settings());
    Item YELLOW_STRANGE_SILK = create("yellow_strange_silk", Item::new, new Item.Settings());
    Item BROWN_STRANGE_SILK = create("brown_strange_silk", Item::new, new Item.Settings());
    Item GREEN_STRANGE_SILK = create("green_strange_silk", Item::new, new Item.Settings());
    Item DARK_GRAY_STRANGE_SILK = create("dark_gray_strange_silk", Item::new, new Item.Settings());
    Item CYAN_STRANGE_SILK = create("cyan_strange_silk", Item::new, new Item.Settings());

    static Item create(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings);
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, Harvestry.id(name), item);
    }

    static Item getDaggerFromType(ShearDaggerItem.Type type) {
        return switch (type) {
            case CANNIBAL   -> CANNIBAL_DAGGER;
            case BOTANICAL  -> BOTANICAL_DAGGER;
            case ALCHEMICAL -> ALCHEMICAL_DAGGER;
            case NONE       -> Items.AIR;
        };
    }

    static void init() {
        ItemGroupEvents.modifyEntriesEvent(HARVESTRY_MAIN_KEY).register(entries -> {
            entries.add(ALCHEMICAL_DAGGER);
            entries.add(BOTANICAL_DAGGER);
            entries.add(CANNIBAL_DAGGER);
            entries.add(WEIRD_WAX);
            entries.add(CURSED_FLESH);
            entries.add(BAFFLING_CRYSTALS);
            entries.add(STRANGE_SILK);
            entries.add(BLACK_STRANGE_SILK);
            entries.add(PURPLE_STRANGE_SILK);
            entries.add(LIGHT_BLUE_STRANGE_SILK);
            entries.add(PINK_STRANGE_SILK);
            entries.add(WHITE_STRANGE_SILK);
            entries.add(MAGENTA_STRANGE_SILK);
            entries.add(BLUE_STRANGE_SILK);
            entries.add(ORANGE_STRANGE_SILK);
            entries.add(LIME_STRANGE_SILK);
            entries.add(RED_STRANGE_SILK);
            entries.add(LIGHT_GRAY_STRANGE_SILK);
            entries.add(YELLOW_STRANGE_SILK);
            entries.add(BROWN_STRANGE_SILK);
            entries.add(GREEN_STRANGE_SILK);
            entries.add(DARK_GRAY_STRANGE_SILK);
            entries.add(CYAN_STRANGE_SILK);
        });
    }
}
