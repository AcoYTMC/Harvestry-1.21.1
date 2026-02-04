package kindling.harvestry.impl.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kindling.harvestry.impl.item.ShearDaggerItem;
import net.minecraft.item.ItemStack;

public record ItemHoldingBlockComponent(ItemStack stack) {
    public static final Codec<ItemHoldingBlockComponent> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                ItemStack.CODEC.fieldOf("held_item").forGetter(ItemHoldingBlockComponent::stack)
            ).apply(builder, ItemHoldingBlockComponent::new));
}
