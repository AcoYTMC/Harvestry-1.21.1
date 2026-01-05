package kindling.harvestry.api.item;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.item.ItemStack;

public interface DoubleWieldedItem {
    BipedEntityModel.ArmPose getArmPose(ItemStack stack, AbstractClientPlayerEntity player);

    ItemStack getSecondStack(ItemStack stack);
    boolean isDouble(ItemStack stack);
}
