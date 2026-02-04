package kindling.harvestry.impl.render;

import kindling.harvestry.impl.block.entity.WitherRibcgeBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.joml.Quaternionf;

public class WitherRibcageBlockEntityRenderer implements BlockEntityRenderer<WitherRibcgeBlockEntity> {
    ItemRenderer itemRenderer;

    public WitherRibcageBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(WitherRibcgeBlockEntity be, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack stack = be.getHeldItem();
        if (stack.isEmpty()) return;

        matrices.push();

        matrices.translate(0.5, 0.5, 0.5);

        long seed = be.getPos().asLong();
        float angleY = (be.getTimer() + seed % 360) % 360;
        float angleX = ((be.getTimer() * 0.7f) + (seed / 2 % 360)) % 360;

        Quaternionf rotation = new Quaternionf()
                .rotateXYZ((float)Math.toRadians(angleX), (float)Math.toRadians(angleY), 0);

        matrices.multiply(rotation);

        float scale = 0.5f;
        matrices.scale(scale, scale, scale);

        itemRenderer.renderItem(stack, ModelTransformationMode.FIXED, 255, overlay, matrices, vertexConsumers, be.getWorld(), 0);

        matrices.pop();
    }
}
