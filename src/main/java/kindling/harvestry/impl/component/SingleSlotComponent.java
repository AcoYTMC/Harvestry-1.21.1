package kindling.harvestry.impl.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kindling.harvestry.impl.item.ShearDaggerItem;

public record SingleSlotComponent(ShearDaggerItem.Type type) {
    public static final Codec<SingleSlotComponent> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Codec.STRING.xmap(ShearDaggerItem.Type::valueOf, ShearDaggerItem.Type::name) // map enum to/from string
                            .fieldOf("stored_item")
                            .forGetter(SingleSlotComponent::type)
            ).apply(builder, SingleSlotComponent::new)
    );
}
