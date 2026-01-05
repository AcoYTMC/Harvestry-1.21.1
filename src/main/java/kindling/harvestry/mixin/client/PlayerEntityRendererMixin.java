package kindling.harvestry.mixin.client;

import kindling.harvestry.api.item.DoubleWieldedItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Inject(method = "getArmPose", at = @At("HEAD"), cancellable = true)
    private static void doubleWieldedArmPose(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<ArmPose> cir) {
        if (player.getMainHandStack().getItem() instanceof DoubleWieldedItem wieldedItem && wieldedItem.isDouble(player.getMainHandStack())) {
            ArmPose pose = wieldedItem.getArmPose(player.getMainHandStack(), player);
            if (pose != null) {
                cir.setReturnValue(pose);
            }
        }

        if (player.getOffHandStack().getItem() instanceof DoubleWieldedItem wieldedItem && wieldedItem.isDouble(player.getMainHandStack())) {
            ArmPose pose = wieldedItem.getArmPose(player.getOffHandStack(), player);
            if (pose != null) {
                cir.setReturnValue(pose);
            }
        }
    }
}
