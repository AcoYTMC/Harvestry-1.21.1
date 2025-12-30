package kindling.harvestry.impl.index;

import kindling.harvestry.impl.Harvestry;
import kindling.harvestry.impl.item.ShearDaggersItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.Function;

public interface HarvestryItems {
    Item SHEAR_DAGGERS = create("shear_daggers", ShearDaggersItem::new, new Item.Settings()
            .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 0, -2.0F))
            .maxCount(1));

    static Item create(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings);
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, Harvestry.id(name), item);
    }

    static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(HarvestryItems::addToolsEntries);
    }

    private static void addToolsEntries(FabricItemGroupEntries entries) {
        entries.add(SHEAR_DAGGERS);
    }
}
