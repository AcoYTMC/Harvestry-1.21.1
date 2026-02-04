package kindling.harvestry.impl.index;

import kindling.harvestry.impl.Harvestry;
import kindling.harvestry.impl.component.ItemHoldingBlockComponent;
import kindling.harvestry.impl.component.SingleSlotComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface HarvestryComponents {
    // Item Components
    ComponentType<SingleSlotComponent> SINGLE_SLOT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Harvestry.id("single_slot"),
            ComponentType.<SingleSlotComponent>builder()
                    .codec(SingleSlotComponent.CODEC)
                    .build()
    );

    // Block Components
    ComponentType<ItemHoldingBlockComponent> ITEM_HOLDING = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Harvestry.id("item_holding"),
            ComponentType.<ItemHoldingBlockComponent>builder()
                    .codec(ItemHoldingBlockComponent.CODEC)
                    .build()
    );

    static void init() {}
}
