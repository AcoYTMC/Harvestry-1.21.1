package kindling.harvestry.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import kindling.harvestry.api.item.DoubleWieldedItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @WrapMethod(method = "renderFirstPersonItem")
    private void doubleRender(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack stack, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Operation<Void> original) {
        boolean bl = player.getMainArm() == Arm.RIGHT;
        ItemStack itemStack = bl ? player.getOffHandStack() : player.getMainHandStack();
        ItemStack itemStack2 = bl ? player.getMainHandStack() : player.getOffHandStack();
        if (stack.getItem() instanceof DoubleWieldedItem wieldedItem && (itemStack == stack || itemStack.isEmpty()) && (itemStack2 == stack || itemStack2.isEmpty()) && wieldedItem.isDouble(stack)) {
            original.call(player, tickDelta, pitch, Hand.MAIN_HAND, swingProgress, stack, equipProgress, matrices, vertexConsumers, light);
            original.call(player, tickDelta, pitch, Hand.OFF_HAND, swingProgress, wieldedItem.getSecondStack(stack), equipProgress, matrices, vertexConsumers, light);
            return;
        }

        original.call(player, tickDelta, pitch, hand, swingProgress, stack, equipProgress, matrices, vertexConsumers, light);
    }
}
