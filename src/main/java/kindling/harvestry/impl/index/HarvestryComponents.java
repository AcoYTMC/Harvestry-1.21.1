package kindling.harvestry.impl.index;

import kindling.harvestry.impl.Harvestry;
import kindling.harvestry.impl.component.SingleSlotComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface HarvestryComponents {
    ComponentType<SingleSlotComponent> SINGLE_SLOT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Harvestry.id("single_slot"),
            ComponentType.<SingleSlotComponent>builder()
                    .codec(SingleSlotComponent.CODEC)
                    .build()
    );

    static void init() {}
}
