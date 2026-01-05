package kindling.harvestry.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import kindling.harvestry.api.item.DoubleWieldedItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(HeldItemFeatureRenderer.class)
public abstract class HeldItemFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T> & ModelWithArms> extends FeatureRenderer<T, M> {
    public HeldItemFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @WrapMethod(method = "renderItem")
    private void doubleRender(LivingEntity entity, ItemStack stack, ModelTransformationMode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Operation<Void> original) {
        boolean bl = entity.getMainArm() == Arm.RIGHT;
        ItemStack itemStack = bl ? entity.getOffHandStack() : entity.getMainHandStack();
        ItemStack itemStack2 = bl ? entity.getMainHandStack() : entity.getOffHandStack();
        if (stack.getItem() instanceof DoubleWieldedItem wieldedItem && (itemStack == stack || itemStack.isEmpty()) && (itemStack2 == stack || itemStack2.isEmpty()) && wieldedItem.isDouble(stack)) {
            original.call(entity, stack, transformationMode, entity.getMainArm(), matrices, vertexConsumers, light);
            original.call(entity, wieldedItem.getSecondStack(stack), getOppositeMode(transformationMode), entity.getMainArm().getOpposite(), matrices, vertexConsumers, light);
            return;
        }

        original.call(entity, stack, transformationMode, arm, matrices, vertexConsumers, light);
    }

    @Unique
    private ModelTransformationMode getOppositeMode(ModelTransformationMode renderMode) {
        return switch (renderMode) {
            case NONE -> ModelTransformationMode.NONE;
            case HEAD -> ModelTransformationMode.HEAD;
            case GUI -> ModelTransformationMode.GUI;
            case GROUND -> ModelTransformationMode.GROUND;
            case FIXED -> ModelTransformationMode.FIXED;
            case THIRD_PERSON_LEFT_HAND -> ModelTransformationMode.THIRD_PERSON_RIGHT_HAND;
            case THIRD_PERSON_RIGHT_HAND -> ModelTransformationMode.THIRD_PERSON_LEFT_HAND;
            case FIRST_PERSON_LEFT_HAND -> ModelTransformationMode.FIRST_PERSON_RIGHT_HAND;
            case FIRST_PERSON_RIGHT_HAND -> ModelTransformationMode.FIRST_PERSON_LEFT_HAND;
            case null -> throw new UnsupportedOperationException();
        };
    }
}
